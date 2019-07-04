package io.github.terraformersmc.terraform.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

/**
 * A leaves block with extended range, permitting leaves to be as far as 13 blocks away from the tree rather than the
 * limit of 6 blocks imposed by vanilla leaves. It also does not block light at all.
 */
public class ExtendedLeavesBlock extends Block {
	public static final int MAX_DISTANCE = 14;
	public static final BooleanProperty PERSISTENT = Properties.PERSISTENT;
	public static final IntProperty DISTANCE = IntProperty.of("distance", 1, MAX_DISTANCE);

	public ExtendedLeavesBlock(Block.Settings settings) {
		super(settings);
		this.setDefaultState(this.stateFactory.getDefaultState().with(DISTANCE, MAX_DISTANCE).with(PERSISTENT, false));
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return state.get(DISTANCE) == MAX_DISTANCE && !state.get(PERSISTENT);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onRandomTick(BlockState state, World world, BlockPos pos, Random random) {
		if (!state.get(PERSISTENT) && state.get(DISTANCE) == MAX_DISTANCE) {
			dropStacks(state, world, pos);
			world.clearBlockState(pos, false);
		}

	}

	@Override
	@SuppressWarnings("deprecation")
	public void onScheduledTick(BlockState state, World world, BlockPos pos, Random random) {
		world.setBlockState(pos, updateDistanceFromLogs(state, world, pos), 3);
	}

	@Override
	@SuppressWarnings("deprecation")
	public int getLightSubtracted(BlockState state, BlockView view, BlockPos pos) {
		return 0;
	}

	@Override
	@SuppressWarnings("deprecation")
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
		int distance = getDistanceFromLog(neighborState) + 1;
		if (distance != 1 || state.get(DISTANCE) != distance) {
			world.getBlockTickScheduler().schedule(pos, this, 1);
		}

		return state;
	}

	private static BlockState updateDistanceFromLogs(BlockState state, IWorld world, BlockPos pos) {
		int distance = MAX_DISTANCE;
		BlockPos.PooledMutable checkPos = BlockPos.PooledMutable.get();
		Throwable caught = null;

		try {
			for (Direction direction : Direction.values()) {
				// .set(pos).move(direction)
				checkPos.method_10114(pos).method_10118(direction);

				distance = Math.min(distance, getDistanceFromLog(world.getBlockState(checkPos)) + 1);
				if (distance == 1) {
					break;
				}
			}
		} catch (Throwable throwable) {
			caught = throwable;
			throw throwable;
		} finally {
			if (checkPos != null) {
				if (caught != null) {
					try {
						checkPos.close();
					} catch (Throwable second) {
						caught.addSuppressed(second);
					}
				} else {
					checkPos.close();
				}
			}
		}

		return state.with(DISTANCE, distance);
	}

	private static int getDistanceFromLog(BlockState state) {
		if (BlockTags.LOGS.contains(state.getBlock())) {
			return 0;
		} else {
			return state.getBlock() instanceof ExtendedLeavesBlock ? state.get(DISTANCE) : MAX_DISTANCE;
		}
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		Blocks.OAK_LEAVES.randomDisplayTick(state, world, pos, random);
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isSideInvisible(BlockState state, BlockState neighborState, Direction offset) {
		// OptiLeaves optimization: Cull faces with leaf block neighbors to reduce geometry in redwood forests
		return neighborState.getBlock() instanceof ExtendedLeavesBlock;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isOpaque(BlockState state) {
		return false;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return Blocks.OAK_LEAVES.getRenderLayer();
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean canSuffocate(BlockState state, BlockView view, BlockPos pos) {
		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean allowsSpawning(BlockState state, BlockView view, BlockPos pos, EntityType<?> entity) {
		return entity == EntityType.OCELOT || entity == EntityType.PARROT;
	}

	@Override
	protected void appendProperties(StateFactory.Builder<Block, BlockState> builder) {
		builder.add(DISTANCE, PERSISTENT);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		return updateDistanceFromLogs(this.getDefaultState().with(PERSISTENT, true), context.getWorld(), context.getBlockPos());
	}
}
