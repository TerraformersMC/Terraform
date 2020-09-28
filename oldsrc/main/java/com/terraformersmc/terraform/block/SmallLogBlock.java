package com.terraformersmc.terraform.block;

import java.util.Random;
import java.util.function.Supplier;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.World;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

// Rather complex: combine the function of leaves, logs, and cobblestone walls.

/**
 * A very complex smaller log block that can connect on all 6 axes, can be waterlogged, and can have leaves embedded.
 * Used for things like the Sakura tree.
 */
public class SmallLogBlock extends BareSmallLogBlock {

	public static final BooleanProperty HAS_LEAVES = BooleanProperty.of("has_leaves");

	private static final int UP_MASK = 1 << Direction.UP.ordinal();
	private static final int DOWN_MASK = 1 << Direction.DOWN.ordinal();
	private static final int NORTH_MASK = 1 << Direction.NORTH.ordinal();
	private static final int EAST_MASK = 1 << Direction.EAST.ordinal();
	private static final int SOUTH_MASK = 1 << Direction.SOUTH.ordinal();
	private static final int WEST_MASK = 1 << Direction.WEST.ordinal();

	protected final VoxelShape[] collisionShapes;
	protected final VoxelShape[] boundingShapes;
	private final Object2IntMap<BlockState> SHAPE_INDEX_CACHE = new Object2IntOpenHashMap<>();

	private final Block leaves;
	private final Supplier<Block> stripped;

	public SmallLogBlock(Block leaves, Supplier<Block> stripped, Settings settings) {
		super(stripped, settings);
		this.setDefaultState(this.getStateManager().getDefaultState()
			.with(UP, false)
			.with(DOWN, false)
			.with(WEST, false)
			.with(EAST, false)
			.with(NORTH, false)
			.with(SOUTH, false)
			.with(WATERLOGGED, false)
			.with(HAS_LEAVES, false)
		);

		this.collisionShapes = this.createShapes(5);
		this.boundingShapes = this.createShapes(5);
		this.leaves = leaves;
		this.stripped= stripped;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (state.get(HAS_LEAVES)) {
			Blocks.OAK_LEAVES.randomDisplayTick(state, world, pos, random);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult result) {
		ItemStack held = player.getStackInHand(hand);

		if (held.getCount() >= 1 && held.getItem() == Item.BLOCK_ITEMS.get(leaves) && !state.get(HAS_LEAVES)) {
			if (!player.isCreative()) {
				held.decrement(1);
			}

			BlockSoundGroup sounds = leaves.getDefaultState().getSoundGroup();
			world.playSound(player, pos, sounds.getPlaceSound(), SoundCategory.BLOCKS, (sounds.getVolume() + 1.0F) / 2.0F, sounds.getPitch() * 0.8F);

			BlockState previous = state;

			state = state.with(HAS_LEAVES, true);

			if (state.get(UP) && world.getBlockState(pos.up()).getBlock() instanceof LeavesBlock) {
				state = state.with(UP, false);
			}

			if (state.get(DOWN) && world.getBlockState(pos.down()).getBlock() instanceof LeavesBlock) {
				state = state.with(DOWN, false);
			}

			if (state.get(WEST) && world.getBlockState(pos.west()).getBlock() instanceof LeavesBlock) {
				state = state.with(WEST, false);
			}

			if (state.get(EAST) && world.getBlockState(pos.east()).getBlock() instanceof LeavesBlock) {
				state = state.with(EAST, false);
			}

			if (state.get(NORTH) && world.getBlockState(pos.north()).getBlock() instanceof LeavesBlock) {
				state = state.with(NORTH, false);
			}

			if (state.get(SOUTH) && world.getBlockState(pos.south()).getBlock() instanceof LeavesBlock) {
				state = state.with(SOUTH, false);
			}

			world.setBlockState(pos, pushEntitiesUpBeforeBlockChange(previous, state, world, pos));

			return ActionResult.SUCCESS;
		} else if(stripped != null && held.getItem() instanceof MiningToolItem) {
			MiningToolItem tool = (MiningToolItem) held.getItem();

			if(tool.isEffectiveOn(state) || tool.getMiningSpeedMultiplier(held, state) > 1.0F) {
				world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);

				if(!world.isClient) {
					BlockState target = stripped.get().getDefaultState()
						.with(BareSmallLogBlock.UP, state.get(BareSmallLogBlock.UP))
						.with(BareSmallLogBlock.DOWN, state.get(BareSmallLogBlock.DOWN))
						.with(BareSmallLogBlock.NORTH, state.get(BareSmallLogBlock.NORTH))
						.with(BareSmallLogBlock.SOUTH, state.get(BareSmallLogBlock.SOUTH))
						.with(BareSmallLogBlock.EAST, state.get(BareSmallLogBlock.EAST))
						.with(BareSmallLogBlock.WEST, state.get(BareSmallLogBlock.WEST))
						.with(BareSmallLogBlock.WATERLOGGED, state.get(BareSmallLogBlock.WATERLOGGED))
						.with(SmallLogBlock.HAS_LEAVES, state.get(SmallLogBlock.HAS_LEAVES));

					world.setBlockState(pos, target);

					held.damage(1, player, consumedPlayer -> consumedPlayer.sendToolBreakStatus(hand));
				}

				return ActionResult.SUCCESS;
			}
		}

		return ActionResult.FAIL;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);

		builder.add(HAS_LEAVES);
	}

	private boolean shouldConnectTo(BlockState state, boolean solid, boolean leaves) {
		Block block = state.getBlock();

		return solid || (!leaves && block instanceof LeavesBlock) || block instanceof BareSmallLogBlock;
	}

	@Override
	public BlockState getNeighborUpdateState(BlockState state, Direction fromDirection, BlockState neighbor, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) {
			world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}

		boolean leaves = state.get(HAS_LEAVES);

		boolean up = fromDirection == Direction.UP && this.shouldConnectTo(neighbor, neighbor.isSideSolidFullSquare(world, neighborPos, Direction.DOWN), leaves) || state.get(UP);
		boolean down = fromDirection == Direction.DOWN && this.shouldConnectTo(neighbor, neighbor.isSideSolidFullSquare(world, neighborPos, Direction.UP), leaves) || state.get(DOWN);
		boolean north = fromDirection == Direction.NORTH && this.shouldConnectTo(neighbor, neighbor.isSideSolidFullSquare(world, neighborPos, Direction.SOUTH), leaves) || state.get(NORTH);
		boolean east = fromDirection == Direction.EAST && this.shouldConnectTo(neighbor, neighbor.isSideSolidFullSquare(world, neighborPos, Direction.WEST), leaves) || state.get(EAST);
		boolean south = fromDirection == Direction.SOUTH && this.shouldConnectTo(neighbor, neighbor.isSideSolidFullSquare(world, neighborPos, Direction.NORTH), leaves) || state.get(SOUTH);
		boolean west = fromDirection == Direction.WEST && this.shouldConnectTo(neighbor, neighbor.isSideSolidFullSquare(world, neighborPos, Direction.EAST), leaves) || state.get(WEST);

		return state
			.with(UP, up)
			.with(DOWN, down)
			.with(NORTH, north)
			.with(EAST, east)
			.with(SOUTH, south)
			.with(WEST, west);
	}

	private int getShapeIndex(BlockState requested) {
		return this.SHAPE_INDEX_CACHE.computeIntIfAbsent(requested, state -> {
			int mask = 0;

			if (state.get(UP)) {
				mask |= UP_MASK;
			}

			if (state.get(DOWN)) {
				mask |= DOWN_MASK;
			}

			if (state.get(NORTH)) {
				mask |= NORTH_MASK;
			}

			if (state.get(EAST)) {
				mask |= EAST_MASK;
			}

			if (state.get(SOUTH)) {
				mask |= SOUTH_MASK;
			}

			if (state.get(WEST)) {
				mask |= WEST_MASK;
			}

			return mask;
		});
	}


	@Override
	@SuppressWarnings("deprecation")
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
		return state.get(HAS_LEAVES) ? VoxelShapes.fullCube() : this.boundingShapes[this.getShapeIndex(state)];
	}

	@Override
	@SuppressWarnings("deprecation")
	public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
		return state.get(HAS_LEAVES) ? VoxelShapes.fullCube() : this.collisionShapes[this.getShapeIndex(state)];
	}

	@Override
	public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
		return this.collisionShapes[this.getShapeIndex(state)];
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return this.collisionShapes[this.getShapeIndex(state)];
	}
}
