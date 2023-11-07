package com.terraformersmc.terraform.leaves.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

/**
 * A leaves block with extended range, permitting leaves to be as far as 13 blocks away from the tree rather than the
 * limit of 6 blocks imposed by vanilla leaves. It also does not block light at all.
 *
 * This class must override every LeavesBlock function that references (compiler inlined) MAX_DISTANCE.
 * The DISTANCE_1_7 property used by LeavesBlock is globally overridden by our MixinProperties.
 */
public class ExtendedLeavesBlock extends LeavesBlock {
	public static final int MAX_DISTANCE = 14;

	public ExtendedLeavesBlock(AbstractBlock.Settings settings) {
		super(settings);

		this.setDefaultState(this.stateManager.getDefaultState()
				.with(DISTANCE, MAX_DISTANCE)
				.with(PERSISTENT, false)
				.with(WATERLOGGED, false));
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return state.get(DISTANCE) == MAX_DISTANCE && !state.get(PERSISTENT);
	}

	@Override
	public boolean shouldDecay(BlockState state) {
		return !state.get(PERSISTENT) && state.get(DISTANCE) == MAX_DISTANCE;
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		world.setBlockState(pos, ExtendedLeavesBlock.updateDistanceFromLogs(state, world, pos), 3);
	}

	@Override
	public int getOpacity(BlockState state, BlockView view, BlockPos pos) {
		return 0;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) {
			world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}

		int distance = ExtendedLeavesBlock.getDistanceFromLog(neighborState) + 1;
		if (distance != 1 || state.get(DISTANCE) != distance) {
			world.scheduleBlockTick(pos, this, 1);
		}

		return state;
	}

	private static BlockState updateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
		int distance = MAX_DISTANCE;
		BlockPos.Mutable mutable = new BlockPos.Mutable();

		for (Direction direction : Direction.values()) {
			mutable.set(pos, direction);
			distance = Math.min(distance, ExtendedLeavesBlock.getDistanceFromLog(world.getBlockState(mutable)) + 1);
			if (distance == 1) {
				break;
			}
		}

		return state.with(DISTANCE, distance);
	}

	private static int getDistanceFromLog(BlockState state) {
		if (state.isIn(BlockTags.LOGS)) {
			return 0;
		}

		Block block = state.getBlock();
		if (block instanceof ExtendedLeavesBlock) {
			return state.get(DISTANCE);
		} else if (block instanceof LeavesBlock) {
			int distance = state.get(DISTANCE);
			return distance < LeavesBlock.MAX_DISTANCE ? distance : MAX_DISTANCE;
		}

		return MAX_DISTANCE;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		FluidState fluidState = context.getWorld().getFluidState(context.getBlockPos());
		BlockState blockState = this.getDefaultState().with(PERSISTENT, true).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);

		return ExtendedLeavesBlock.updateDistanceFromLogs(blockState, context.getWorld(), context.getBlockPos());
	}

	@Override
	public boolean isSideInvisible(BlockState state, BlockState neighborState, Direction offset) {
		// OptiLeaves optimization: Cull faces with leaf block neighbors to reduce geometry in redwood forests
		return neighborState.getBlock() instanceof ExtendedLeavesBlock;
	}
}
