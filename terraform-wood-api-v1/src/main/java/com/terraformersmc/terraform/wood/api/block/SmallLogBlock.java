package com.terraformersmc.terraform.wood.api.block;

import com.terraformersmc.terraform.wood.mixin.InvokerLeavesBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

// Rather complex: combine the function of leaves, logs, and cobblestone walls.

/**
 * A very complex smaller log block that can connect on all 6 axes, can be waterlogged, and can have leaves embedded.
 * Used for things like the Sakura tree.
 */
@SuppressWarnings("unused")
public class SmallLogBlock extends BareSmallLogBlock {
	public static final BooleanProperty HAS_LEAVES = BooleanProperty.create("has_leaves");

	private final Block leaves;

	public SmallLogBlock(Block leaves, Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(AXIS, Direction.Axis.Y)
				.setValue(UP, false)
				.setValue(DOWN, false)
				.setValue(WEST, false)
				.setValue(EAST, false)
				.setValue(NORTH, false)
				.setValue(SOUTH, false)
				.setValue(WATERLOGGED, false)
				.setValue(HAS_LEAVES, false)
		);

		this.leaves = leaves;
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (level.isClientSide() && state.getValue(HAS_LEAVES)) {
			/*
			 * Below here this is a copy of LeavesBlock.animateTick() from vanilla.
			 * This is because some other mods crash when we call the vanilla method on a non-LeavesBlock.
			 * To merge changes, you should typically be able to just replace with a copy from vanilla and
			 * fix up any access issues with invokers or access wideners.
			 */
			super.animateTick(state, level, pos, random);

			BlockPos below = pos.below();
			BlockState belowState = level.getBlockState(below);

			InvokerLeavesBlock.callMakeDrippingWaterParticles(level, pos, random, belowState, below);
			((InvokerLeavesBlock) this.leaves).callMakeFallingLeavesParticles(level, pos, random, belowState, below);
		}
	}

	@Override
	public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		ItemStack held = player.getUseItem();

		if (held.getCount() >= 1 && held.getItem() == Item.BY_BLOCK.get(leaves) && !state.getValue(HAS_LEAVES)) {
			if (!player.isCreative()) {
				held.shrink(1);
			}

			SoundType sounds = leaves.defaultBlockState().getSoundType();
			level.playSound(player, pos, sounds.getPlaceSound(), SoundSource.BLOCKS, (sounds.getVolume() + 1.0F) / 2.0F, sounds.getPitch() * 0.8F);

			BlockState previous = state;

			state = state.setValue(HAS_LEAVES, true);

			if (state.getValue(UP) && level.getBlockState(pos.above()).getBlock() instanceof LeavesBlock) {
				state = state.setValue(UP, false);
			}

			if (state.getValue(DOWN) && level.getBlockState(pos.below()).getBlock() instanceof LeavesBlock) {
				state = state.setValue(DOWN, false);
			}

			if (state.getValue(WEST) && level.getBlockState(pos.west()).getBlock() instanceof LeavesBlock) {
				state = state.setValue(WEST, false);
			}

			if (state.getValue(EAST) && level.getBlockState(pos.east()).getBlock() instanceof LeavesBlock) {
				state = state.setValue(EAST, false);
			}

			if (state.getValue(NORTH) && level.getBlockState(pos.north()).getBlock() instanceof LeavesBlock) {
				state = state.setValue(NORTH, false);
			}

			if (state.getValue(SOUTH) && level.getBlockState(pos.south()).getBlock() instanceof LeavesBlock) {
				state = state.setValue(SOUTH, false);
			}

			level.setBlockAndUpdate(pos, pushEntitiesUp(previous, state, level, pos));

			return InteractionResult.SUCCESS;
		}

		return InteractionResult.FAIL;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);

		builder.add(HAS_LEAVES);
	}

	protected boolean shouldConnectTo(BlockState state, boolean solid, boolean leaves) {
		Block block = state.getBlock();

		return solid || (!leaves && block instanceof LeavesBlock) || block instanceof BareSmallLogBlock;
	}

	@Override
	public BlockState getNeighborUpdateState(BlockState state, Direction fromDirection, BlockState neighbor, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		boolean leaves = state.getValue(HAS_LEAVES);

		boolean up = fromDirection == Direction.UP && this.shouldConnectTo(neighbor, neighbor.isFaceSturdy(level, neighborPos, Direction.DOWN), leaves) || state.getValue(UP);
		boolean down = fromDirection == Direction.DOWN && this.shouldConnectTo(neighbor, neighbor.isFaceSturdy(level, neighborPos, Direction.UP), leaves) || state.getValue(DOWN);
		boolean north = fromDirection == Direction.NORTH && this.shouldConnectTo(neighbor, neighbor.isFaceSturdy(level, neighborPos, Direction.SOUTH), leaves) || state.getValue(NORTH);
		boolean east = fromDirection == Direction.EAST && this.shouldConnectTo(neighbor, neighbor.isFaceSturdy(level, neighborPos, Direction.WEST), leaves) || state.getValue(EAST);
		boolean south = fromDirection == Direction.SOUTH && this.shouldConnectTo(neighbor, neighbor.isFaceSturdy(level, neighborPos, Direction.NORTH), leaves) || state.getValue(SOUTH);
		boolean west = fromDirection == Direction.WEST && this.shouldConnectTo(neighbor, neighbor.isFaceSturdy(level, neighborPos, Direction.EAST), leaves) || state.getValue(WEST);

		return state
				.setValue(UP, up)
				.setValue(DOWN, down)
				.setValue(NORTH, north)
				.setValue(EAST, east)
				.setValue(SOUTH, south)
				.setValue(WEST, west);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return state.getValue(HAS_LEAVES) ? Shapes.block() : this.boundingShapes[this.getShapeIndex(state)];
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return state.getValue(HAS_LEAVES) ? Shapes.block() : this.collisionShapes[this.getShapeIndex(state)];
	}

	@Override
	public VoxelShape getOcclusionShape(BlockState state) {
		return this.collisionShapes[this.getShapeIndex(state)];
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return this.collisionShapes[this.getShapeIndex(state)];
	}
}
