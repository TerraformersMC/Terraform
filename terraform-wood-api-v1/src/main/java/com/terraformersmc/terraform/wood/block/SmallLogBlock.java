package com.terraformersmc.terraform.wood.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.function.Supplier;

// Rather complex: combine the function of leaves, logs, and cobblestone walls.

/**
 * A very complex smaller log block that can connect on all 6 axes, can be waterlogged, and can have leaves embedded.
 * Used for things like the Sakura tree.
 */
public class SmallLogBlock extends BareSmallLogBlock {
	public static final BooleanProperty HAS_LEAVES = BooleanProperty.of("has_leaves");

	private final Block leaves;

	public SmallLogBlock(Block leaves, Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState()
				.with(AXIS, Direction.Axis.Y)
				.with(UP, false)
				.with(DOWN, false)
				.with(WEST, false)
				.with(EAST, false)
				.with(NORTH, false)
				.with(SOUTH, false)
				.with(WATERLOGGED, false)
				.with(HAS_LEAVES, false)
		);

		this.leaves = leaves;
	}

	/**
	 * <p>This constructor is deprecated in favor of using the new SmallLogBlock.of() factories
	 * and Fabric's StrippableBlockRegistry.</p>
	 *
	 * <pre>{@code
	 *     logBlock = SmallLogBlock.of(leaves, settings, color);
	 *     strippedBlock = SmallLogBlock.of(leaves, settings, color);
	 *     StrippableBlockRegistry.register(logBlock, strippedBlock);
	 * }</pre>
	 *
	 * @param leaves Block used for leaves on log
	 * @param stripped Supplier of default BlockState for stripped variant
	 * @param settings Block Settings for log
	 */
	@Deprecated(forRemoval = true)
	public SmallLogBlock(Block leaves, Supplier<Block> stripped, Settings settings) {
		this(leaves, settings);

		if (stripped != null) {
			StrippableBlockRegistry.register(this, stripped.get());
		}
	}

	/**
	 * Factory to create a SmallLogBlock with the provided settings and
	 * the same map color on the top/bottom and sides.
	 *
	 * @param leaves Block used for leaves on log
	 * @param settings Block Settings for log
	 * @param color Map color for all faces of log
	 * @return New SmallLogBlock
	 */
	public static SmallLogBlock of(Block leaves, Block.Settings settings, MapColor color) {
		return new SmallLogBlock(leaves, settings.mapColor(color));
	}

	/**
	 * Factory to create a SmallLogBlock with default settings and
	 * different map colors on the top/bottom versus the sides.
	 *
	 * @param leaves Block used for leaves on log
	 * @param wood Map color for non-bark faces of log (ends)
	 * @param bark Map color for bark faces of log (sides)
	 * @return New SmallLogBlock
	 */
	public static SmallLogBlock of(Block leaves, MapColor wood, MapColor bark) {
		return new SmallLogBlock(leaves,
				Block.Settings.of(
						Material.WOOD,
						(state) -> state.get(HAS_LEAVES) ? leaves.getDefaultMapColor() :
								state.get(UP) ? wood : bark
				).strength(2.0F).sounds(BlockSoundGroup.WOOD)
		);
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (!state.get(HAS_LEAVES)) {
			return;
		}
		// Below here this is a copy of LeavesBlock.randomDisplayTick() from vanilla.
		// This is because some other mods crash when we call the vanilla method on a non-LeavesBlock.
		// To merge changes, you should typically be able to just replace vv with a copy from vanilla.
		if (!world.hasRain(pos.up())) {
			return;
		}
		if (random.nextInt(15) != 1) {
			return;
		}
		BlockPos lv = pos.down();
		BlockState lv2 = world.getBlockState(lv);
		if (lv2.isOpaque() && lv2.isSideSolidFullSquare(world, lv, Direction.UP)) {
			return;
		}
		ParticleUtil.spawnParticle(world, pos, random, ParticleTypes.DRIPPING_WATER);
	}

	@Override
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
		}

		return ActionResult.FAIL;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);

		builder.add(HAS_LEAVES);
	}

	protected boolean shouldConnectTo(BlockState state, boolean solid, boolean leaves) {
		Block block = state.getBlock();

		return solid || (!leaves && block instanceof LeavesBlock) || block instanceof BareSmallLogBlock;
	}

	@Override
	public BlockState getNeighborUpdateState(BlockState state, Direction fromDirection, BlockState neighbor, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) {
			world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
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

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
		return state.get(HAS_LEAVES) ? VoxelShapes.fullCube() : this.boundingShapes[this.getShapeIndex(state)];
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
		return state.get(HAS_LEAVES) ? VoxelShapes.fullCube() : this.collisionShapes[this.getShapeIndex(state)];
	}

	@Override
	public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
		return this.collisionShapes[this.getShapeIndex(state)];
	}

	@Override
	public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return this.collisionShapes[this.getShapeIndex(state)];
	}
}
