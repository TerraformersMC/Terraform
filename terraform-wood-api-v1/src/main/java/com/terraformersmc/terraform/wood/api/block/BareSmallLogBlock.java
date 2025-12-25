package com.terraformersmc.terraform.wood.api.block;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

// Rather complex: combine the function of logs, and cobblestone walls.

/**
 * A very complex smaller log block that can connect on all 6 axes and can be waterlogged.
 * Used for things like the Saguaro Cactus.
 */
public class BareSmallLogBlock extends Block implements SimpleWaterloggedBlock {
	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
	public static final BooleanProperty UP = BlockStateProperties.UP;
	public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
	public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
	public static final BooleanProperty EAST = BlockStateProperties.EAST;
	public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
	public static final BooleanProperty WEST = BlockStateProperties.WEST;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

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

	public BareSmallLogBlock(BlockBehaviour.Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(AXIS, Direction.Axis.Y)
				.setValue(UP, false)
				.setValue(DOWN, false)
				.setValue(WEST, false)
				.setValue(EAST, false)
				.setValue(NORTH, false)
				.setValue(SOUTH, false)
				.setValue(WATERLOGGED, false)
		);

		this.collisionShapes = this.createShapes(LOG_RADIUS);
		this.boundingShapes = this.createShapes(LOG_RADIUS);
	}

	/**
	 * Factory to create a BareSmallLogBlock with default settings and
	 * the same map color on all block faces.
	 *
	 * @deprecated Use {@linkplain PillarLogHelper#createSettings(MapColor)}
	 * @param color Map color for all faces of log
	 * @return New BareSmallLogBlock
	 */
	@Deprecated(since = "12.0.0", forRemoval = true)
	public static BareSmallLogBlock of(MapColor color) {
		return new BareSmallLogBlock(BlockBehaviour.Properties.of()
				.mapColor(color)
				.strength(2.0F)
				.sound(SoundType.WOOD)
				.ignitedByLava()
		);
	}

	/**
	 * Factory to create a BareSmallLogBlock with default settings and
	 * different map colors on the top/bottom versus the sides.
	 *
	 * @deprecated Use {@linkplain PillarLogHelper#createSettings(MapColor, MapColor)}
	 * @param wood Map color for non-bark faces of log (ends)
	 * @param bark Map color for bark faces of log (sides)
	 * @return New BareSmallLogBlock
	 */
	@Deprecated(since = "12.0.0", forRemoval = true)
	public static BareSmallLogBlock of(MapColor wood, MapColor bark) {
		return new BareSmallLogBlock(BlockBehaviour.Properties.of()
				.mapColor((state) -> state.getValue(UP) ? wood : bark)
				.strength(2.0F)
				.sound(SoundType.WOOD)
				.ignitedByLava()
		);
	}

	protected int getShapeIndex(BlockState requested) {
		return this.SHAPE_INDEX_CACHE.computeIntIfAbsent(requested, state -> {
			int mask = 0;

			if (state.getValue(UP)) {
				mask |= UP_MASK;
			}

			if (state.getValue(DOWN)) {
				mask |= DOWN_MASK;
			}

			if (state.getValue(NORTH)) {
				mask |= NORTH_MASK;
			}

			if (state.getValue(EAST)) {
				mask |= EAST_MASK;
			}

			if (state.getValue(SOUTH)) {
				mask |= SOUTH_MASK;
			}

			if (state.getValue(WEST)) {
				mask |= WEST_MASK;
			}

			return mask;
		});
	}

	public VoxelShape[] createShapes(double radius) {
		double lower = 8.0 - radius;
		double upper = 8.0 + radius;

		VoxelShape center = Block.box(lower, lower, lower, upper, upper, upper);

		VoxelShape down = Block.box(lower, 0.0, lower, upper, lower, upper);
		VoxelShape up = Block.box(lower, upper, lower, upper, 16.0, upper);

		// Minus Z: North
		VoxelShape north = Block.box(lower, lower, 0.0, upper, upper, lower);
		VoxelShape south = Block.box(lower, lower, upper, upper, upper, 16.0);

		// Minus X: West
		VoxelShape west = Block.box(0.0, lower, lower, lower, upper, upper);
		VoxelShape east = Block.box(upper, lower, lower, 16.0, upper, upper);

		VoxelShape[] shapes = new VoxelShape[64];

		for (int i = 0; i < 64; i++) {
			VoxelShape shape = center;

			if ((i & DOWN_MASK) != 0) {
				shape = Shapes.or(shape, down);
			}

			if ((i & UP_MASK) != 0) {
				shape = Shapes.or(shape, up);
			}

			if ((i & NORTH_MASK) != 0) {
				shape = Shapes.or(shape, north);
			}

			if ((i & SOUTH_MASK) != 0) {
				shape = Shapes.or(shape, south);
			}

			if ((i & WEST_MASK) != 0) {
				shape = Shapes.or(shape, west);
			}

			if ((i & EAST_MASK) != 0) {
				shape = Shapes.or(shape, east);
			}

			shapes[i] = shape;
		}

		return shapes;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);

		builder.add(AXIS, UP, DOWN, NORTH, SOUTH, EAST, WEST, WATERLOGGED);
	}

	private boolean shouldConnectTo(BlockState state, boolean solid) {
		Block block = state.getBlock();

		return solid || block instanceof BareSmallLogBlock;
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
		super.setPlacedBy(world, pos, state, entity, stack);

		for (Direction direction : Direction.values()) {
			BlockPos offsetPos = pos.relative(direction);
			BlockState offsetState = world.getBlockState(offsetPos);

			if (offsetState.getBlock() instanceof BareSmallLogBlock) {
				world.setBlockAndUpdate(offsetPos, getNeighborUpdateState(
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
	public BlockState getStateForPlacement(BlockPlaceContext context) {

		LevelReader world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());

		if (context.getPlayer() == null) {
			return fluid.getType().equals(Fluids.WATER) ? this.defaultBlockState().setValue(WATERLOGGED, true) : this.defaultBlockState();
		}

		BlockPos upPos = pos.above();
		BlockPos downPos = pos.below();
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

		boolean up = this.shouldConnectTo(upState, upState.isFaceSturdy(world, upPos, Direction.UP));
		boolean down = this.shouldConnectTo(downState, downState.isFaceSturdy(world, downPos, Direction.DOWN));
		boolean north = this.shouldConnectTo(northState, northState.isFaceSturdy(world, northPos, Direction.SOUTH));
		boolean east = this.shouldConnectTo(eastState, eastState.isFaceSturdy(world, eastPos, Direction.WEST));
		boolean south = this.shouldConnectTo(southState, southState.isFaceSturdy(world, southPos, Direction.NORTH));
		boolean west = this.shouldConnectTo(westState, westState.isFaceSturdy(world, westPos, Direction.EAST));

		return this.defaultBlockState()
				.setValue(UP, up)
				.setValue(DOWN, down)
				.setValue(NORTH, north)
				.setValue(EAST, east)
				.setValue(SOUTH, south)
				.setValue(WEST, west)
				.setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return switch (rotation) {
			case CLOCKWISE_180 ->
					state.setValue(NORTH, state.getValue(SOUTH)).setValue(EAST, state.getValue(WEST)).setValue(SOUTH, state.getValue(NORTH)).setValue(WEST, state.getValue(EAST));
			case COUNTERCLOCKWISE_90 ->
					state.setValue(NORTH, state.getValue(EAST)).setValue(EAST, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(WEST)).setValue(WEST, state.getValue(NORTH));
			case CLOCKWISE_90 ->
					state.setValue(NORTH, state.getValue(WEST)).setValue(EAST, state.getValue(NORTH)).setValue(SOUTH, state.getValue(EAST)).setValue(WEST, state.getValue(SOUTH));
			default -> state;
		};
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return switch (mirror) {
			case LEFT_RIGHT -> state.setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH));
			case FRONT_BACK -> state.setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST));
			default -> super.mirror(state, mirror);
		};
	}

	@Override
	public boolean isPathfindable(BlockState state, PathComputationType type) {
		return false;
	}

	public BlockState getNeighborUpdateState(BlockState state, Direction fromDirection, BlockState neighbor, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		if (state.getValue(WATERLOGGED)) {
			world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}

		boolean up = fromDirection == Direction.UP && this.shouldConnectTo(neighbor, neighbor.isFaceSturdy(world, neighborPos, Direction.DOWN)) || state.getValue(UP);
		boolean down = fromDirection == Direction.DOWN && this.shouldConnectTo(neighbor, neighbor.isFaceSturdy(world, neighborPos, Direction.UP)) || state.getValue(DOWN);
		boolean north = fromDirection == Direction.NORTH && this.shouldConnectTo(neighbor, neighbor.isFaceSturdy(world, neighborPos, Direction.SOUTH)) || state.getValue(NORTH);
		boolean east = fromDirection == Direction.EAST && this.shouldConnectTo(neighbor, neighbor.isFaceSturdy(world, neighborPos, Direction.WEST)) || state.getValue(EAST);
		boolean south = fromDirection == Direction.SOUTH && this.shouldConnectTo(neighbor, neighbor.isFaceSturdy(world, neighborPos, Direction.NORTH)) || state.getValue(SOUTH);
		boolean west = fromDirection == Direction.WEST && this.shouldConnectTo(neighbor, neighbor.isFaceSturdy(world, neighborPos, Direction.EAST)) || state.getValue(WEST);

		return state
				.setValue(UP, up)
				.setValue(DOWN, down)
				.setValue(NORTH, north)
				.setValue(EAST, east)
				.setValue(SOUTH, south)
				.setValue(WEST, west);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state) {
		return !state.getValue(WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext context) {
		return this.boundingShapes[this.getShapeIndex(state)];
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext context) {
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
