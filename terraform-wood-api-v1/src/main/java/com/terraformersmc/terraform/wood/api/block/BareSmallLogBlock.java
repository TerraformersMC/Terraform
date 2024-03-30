package com.terraformersmc.terraform.wood.api.block;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

// Rather complex: combine the function of logs, and cobblestone walls.

/**
 * A very complex smaller log block that can connect on all 6 axes and can be waterlogged.
 * Used for things like the Saguaro Cactus.
 */
public class BareSmallLogBlock extends Block implements Waterloggable {
	public static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;
	public static final BooleanProperty UP = Properties.UP;
	public static final BooleanProperty DOWN = Properties.DOWN;
	public static final BooleanProperty NORTH = Properties.NORTH;
	public static final BooleanProperty EAST = Properties.EAST;
	public static final BooleanProperty SOUTH = Properties.SOUTH;
	public static final BooleanProperty WEST = Properties.WEST;
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

	protected static final int UP_MASK = 1 << Direction.UP.ordinal();
	protected static final int DOWN_MASK = 1 << Direction.DOWN.ordinal();
	protected static final int NORTH_MASK = 1 << Direction.NORTH.ordinal();
	protected static final int EAST_MASK = 1 << Direction.EAST.ordinal();
	protected static final int SOUTH_MASK = 1 << Direction.SOUTH.ordinal();
	protected static final int WEST_MASK = 1 << Direction.WEST.ordinal();

	protected final int LOG_RADIUS = 5;

	protected final VoxelShape[] collisionShapes;
	protected final VoxelShape[] boundingShapes;
	protected final Object2IntMap<BlockState> SHAPE_INDEX_CACHE = new Object2IntOpenHashMap<>();

	public BareSmallLogBlock(AbstractBlock.Settings settings) {
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
		);

		this.collisionShapes = this.createShapes(LOG_RADIUS);
		this.boundingShapes = this.createShapes(LOG_RADIUS);
	}

	/**
	 * Factory to create a BareSmallLogBlock with default settings and
	 * the same map color on all block faces.
	 *
	 * @param color Map color for all faces of log
	 * @return New BareSmallLogBlock
	 */
	public static BareSmallLogBlock of(MapColor color) {
		return new BareSmallLogBlock(AbstractBlock.Settings.create()
				.mapColor(color)
				.strength(2.0F)
				.sounds(BlockSoundGroup.WOOD)
				.burnable()
		);
	}

	/**
	 * Factory to create a BareSmallLogBlock with default settings and
	 * different map colors on the top/bottom versus the sides.
	 *
	 * @param wood Map color for non-bark faces of log (ends)
	 * @param bark Map color for bark faces of log (sides)
	 * @return New BareSmallLogBlock
	 */
	public static BareSmallLogBlock of(MapColor wood, MapColor bark) {
		return new BareSmallLogBlock(AbstractBlock.Settings.create()
				.mapColor((state) -> state.get(UP) ? wood : bark)
				.strength(2.0F)
				.sounds(BlockSoundGroup.WOOD)
				.burnable()
		);
	}

	protected int getShapeIndex(BlockState requested) {
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

	public VoxelShape[] createShapes(double radius) {
		double lower = 8.0 - radius;
		double upper = 8.0 + radius;

		VoxelShape center = Block.createCuboidShape(lower, lower, lower, upper, upper, upper);

		VoxelShape down = Block.createCuboidShape(lower, 0.0, lower, upper, lower, upper);
		VoxelShape up = Block.createCuboidShape(lower, upper, lower, upper, 16.0, upper);

		// Minus Z: North
		VoxelShape north = Block.createCuboidShape(lower, lower, 0.0, upper, upper, lower);
		VoxelShape south = Block.createCuboidShape(lower, lower, upper, upper, upper, 16.0);

		// Minus X: West
		VoxelShape west = Block.createCuboidShape(0.0, lower, lower, lower, upper, upper);
		VoxelShape east = Block.createCuboidShape(upper, lower, lower, 16.0, upper, upper);

		VoxelShape[] shapes = new VoxelShape[64];

		for (int i = 0; i < 64; i++) {
			VoxelShape shape = center;

			if ((i & DOWN_MASK) != 0) {
				shape = VoxelShapes.union(shape, down);
			}

			if ((i & UP_MASK) != 0) {
				shape = VoxelShapes.union(shape, up);
			}

			if ((i & NORTH_MASK) != 0) {
				shape = VoxelShapes.union(shape, north);
			}

			if ((i & SOUTH_MASK) != 0) {
				shape = VoxelShapes.union(shape, south);
			}

			if ((i & WEST_MASK) != 0) {
				shape = VoxelShapes.union(shape, west);
			}

			if ((i & EAST_MASK) != 0) {
				shape = VoxelShapes.union(shape, east);
			}

			shapes[i] = shape;
		}

		return shapes;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);

		builder.add(AXIS, UP, DOWN, NORTH, SOUTH, EAST, WEST, WATERLOGGED);
	}

	private boolean shouldConnectTo(BlockState state, boolean solid) {
		Block block = state.getBlock();

		return solid || block instanceof BareSmallLogBlock;
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
		super.onPlaced(world, pos, state, entity, stack);

		for (Direction direction : Direction.values()) {
			BlockPos offsetPos = pos.offset(direction);
			BlockState offsetState = world.getBlockState(offsetPos);

			if (offsetState.getBlock() instanceof BareSmallLogBlock) {
				world.setBlockState(offsetPos, getNeighborUpdateState(
					offsetState,
					direction.getOpposite(),
					state,
					world,
					offsetPos,
					pos
				));
			}
		}
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {

		WorldView world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		FluidState fluid = context.getWorld().getFluidState(context.getBlockPos());

		if (context.getPlayer() == null) {
			return fluid.getFluid().equals(Fluids.WATER) ? this.getDefaultState().with(WATERLOGGED, true) : this.getDefaultState();
		}

		BlockPos upPos = pos.up();
		BlockPos downPos = pos.down();
		BlockPos northPos = pos.north();
		BlockPos eastPos = pos.east();
		BlockPos southPos = pos.south();
		BlockPos westPos = pos.west();

		BlockState upState = world.getBlockState(upPos);
		BlockState downState = world.getBlockState(downPos);
		BlockState northState = world.getBlockState(northPos);
		BlockState eastState = world.getBlockState(eastPos);
		BlockState southState = world.getBlockState(southPos);
		BlockState westState = world.getBlockState(westPos);

		boolean up = this.shouldConnectTo(upState, upState.isSideSolidFullSquare(world, upPos, Direction.UP));
		boolean down = this.shouldConnectTo(downState, downState.isSideSolidFullSquare(world, downPos, Direction.DOWN));
		boolean north = this.shouldConnectTo(northState, northState.isSideSolidFullSquare(world, northPos, Direction.SOUTH));
		boolean east = this.shouldConnectTo(eastState, eastState.isSideSolidFullSquare(world, eastPos, Direction.WEST));
		boolean south = this.shouldConnectTo(southState, southState.isSideSolidFullSquare(world, southPos, Direction.NORTH));
		boolean west = this.shouldConnectTo(westState, westState.isSideSolidFullSquare(world, westPos, Direction.EAST));

		return this.getDefaultState()
				.with(UP, up)
				.with(DOWN, down)
				.with(NORTH, north)
				.with(EAST, east)
				.with(SOUTH, south)
				.with(WEST, west)
				.with(WATERLOGGED, fluid.getFluid() == Fluids.WATER);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return switch (rotation) {
			case CLOCKWISE_180 ->
					state.with(NORTH, state.get(SOUTH)).with(EAST, state.get(WEST)).with(SOUTH, state.get(NORTH)).with(WEST, state.get(EAST));
			case COUNTERCLOCKWISE_90 ->
					state.with(NORTH, state.get(EAST)).with(EAST, state.get(SOUTH)).with(SOUTH, state.get(WEST)).with(WEST, state.get(NORTH));
			case CLOCKWISE_90 ->
					state.with(NORTH, state.get(WEST)).with(EAST, state.get(NORTH)).with(SOUTH, state.get(EAST)).with(WEST, state.get(SOUTH));
			default -> state;
		};
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return switch (mirror) {
			case LEFT_RIGHT -> state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
			case FRONT_BACK -> state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
			default -> super.mirror(state, mirror);
		};
	}

	@Override
	public boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}

	public BlockState getNeighborUpdateState(BlockState state, Direction fromDirection, BlockState neighbor, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) {
			world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}

		boolean up = fromDirection == Direction.UP && this.shouldConnectTo(neighbor, neighbor.isSideSolidFullSquare(world, neighborPos, Direction.DOWN)) || state.get(UP);
		boolean down = fromDirection == Direction.DOWN && this.shouldConnectTo(neighbor, neighbor.isSideSolidFullSquare(world, neighborPos, Direction.UP)) || state.get(DOWN);
		boolean north = fromDirection == Direction.NORTH && this.shouldConnectTo(neighbor, neighbor.isSideSolidFullSquare(world, neighborPos, Direction.SOUTH)) || state.get(NORTH);
		boolean east = fromDirection == Direction.EAST && this.shouldConnectTo(neighbor, neighbor.isSideSolidFullSquare(world, neighborPos, Direction.WEST)) || state.get(EAST);
		boolean south = fromDirection == Direction.SOUTH && this.shouldConnectTo(neighbor, neighbor.isSideSolidFullSquare(world, neighborPos, Direction.NORTH)) || state.get(SOUTH);
		boolean west = fromDirection == Direction.WEST && this.shouldConnectTo(neighbor, neighbor.isSideSolidFullSquare(world, neighborPos, Direction.EAST)) || state.get(WEST);

		return state
				.with(UP, up)
				.with(DOWN, down)
				.with(NORTH, north)
				.with(EAST, east)
				.with(SOUTH, south)
				.with(WEST, west);
	}

	@Override
	public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
		return !state.get(WATERLOGGED);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
		return this.boundingShapes[this.getShapeIndex(state)];
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
		return this.collisionShapes[this.getShapeIndex(state)];
	}

	/**
	 * You can call this method on Terraformers API small logs to get the log radius.
	 * The trunk will occupy 2*getLogRadius() centered in the block.
	 *
	 * <pre>{@code
	 *     int logRadius = 8;
	 *     if (block instanceof BareSmallLogBlock smallLogBlock) {
	 *         logRadius = smallLogBlock.getLogRadius();
	 *     }
	 * }</pre>
	 *
	 * @return The radius of the log
	 */
	@SuppressWarnings("unused")
	public int getLogRadius() {
		return LOG_RADIUS;
	}
}
