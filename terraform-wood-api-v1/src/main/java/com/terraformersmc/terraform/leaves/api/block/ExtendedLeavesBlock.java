package com.terraformersmc.terraform.leaves.api.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.terraformersmc.terraform.wood.api.block.SmallLogBlock;
import java.util.Optional;
import java.util.OptionalInt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

/**
 * <p>
 * A leaves block with extended range, permitting leaves to be as far as 13 blocks away from the tree rather than the
 * limit of 6 blocks imposed by vanilla leaves.  Enabling the opti-leaves feature can reduce geometry and give better
 * rendering performance in dense forests of large trees.  By default, extended leaves are transparent (do not block
 * light at all).  If the optional leaf_particle field is present, the custom particle is used; otherwise a tinted
 * falling leaf particle is used.
 * </p><p>
 * Be aware when using the extended leaf range, there are now two distance properties which combine to provide the
 * extended distance range.  Mod authors should be careful not to directly rely on or set the value of the
 * {@link LeavesBlock#DISTANCE} property when using this class.  Instead, use the provided
 * {@link ExtendedLeavesBlock#getExtendedDistance(BlockState)} and
 * {@link ExtendedLeavesBlock#setExtendedDistance(BlockState, int)} methods, which will sum up and properly allocate
 * the total distance to the two properties.  For the total available extended distance constant (14), use
 * {@link ExtendedLeavesBlock#MAX_TOTAL_DISTANCE} instead of either MAX_DISTANCE or MAX_EXTENDED_DISTANCE.
 * </p>
 */
/* This class must override every LeavesBlock function that references (compiler inlined) MAX_DISTANCE.
 * The DECAY_DISTANCE property used by LeavesBlock is complemented by our EXTENDED_DISTANCE property.
 */
@SuppressWarnings({"unused", "OptionalUsedAsFieldOrParameterType"})
public class ExtendedLeavesBlock extends LeavesBlock {
	public static final MapCodec<ExtendedLeavesBlock> CODEC = RecordCodecBuilder.mapCodec(
			(instance) -> instance.group(
							ExtraCodecs.floatRange(0.0f, 1.0f).fieldOf("leaf_particle_chance")
									.forGetter(arg -> arg.leafParticleChance),
							ParticleTypes.CODEC.optionalFieldOf("leaf_particle")
									.forGetter(arg -> arg.leafParticleOptions),
							Codec.BOOL.fieldOf("opti")
									.forGetter(arg -> arg.opti),
							Codec.BOOL.fieldOf("transparent")
									.forGetter(arg -> arg.transparent),
							ExtendedLeavesBlock.propertiesCodec()
					)
					.apply(instance, ExtendedLeavesBlock::new));

	public static final int MAX_EXTENDED_DISTANCE = 7;
	public static final int MAX_TOTAL_DISTANCE = DECAY_DISTANCE + MAX_EXTENDED_DISTANCE;
	public static final IntegerProperty EXTENDED_DISTANCE = IntegerProperty.create("extended_distance", 0, MAX_EXTENDED_DISTANCE);

	protected final Optional<ParticleOptions> leafParticleOptions;
	protected final boolean opti;
	protected final boolean transparent;

	/**
	 * Full options constructor for Terraform ExtendedLeaves.
	 *
	 * @param leafParticleChance The relative likelihood of each leaf block spawning leaf particles
	 * @param leafParticleOptions Optional {@link ParticleOptions} to use instead of biome tinted falling leaf particles
	 * @param opti Whether to enable the opti-leaves feature
	 * @param transparent Whether to allow light to pass freely through the block
	 * @param properties The block properties
	 */
	public ExtendedLeavesBlock(float leafParticleChance, Optional<ParticleOptions> leafParticleOptions, boolean opti, boolean transparent, BlockBehaviour.Properties properties) {
		super(leafParticleChance, properties);

		this.leafParticleOptions = leafParticleOptions;
		this.opti = opti;
		this.transparent = transparent;

		this.registerDefaultState(this.stateDefinition.any()
				.setValue(DISTANCE, DECAY_DISTANCE)
				.setValue(EXTENDED_DISTANCE, MAX_EXTENDED_DISTANCE)
				.setValue(PERSISTENT, false)
				.setValue(WATERLOGGED, false));
	}

	/**
	 * Default options constructor for Terraform ExtendedLeaves:
	 * <ul>
	 * <li>set leaf particle chance to {@code 0.01}</li>
	 * <li>use default biome tinted falling leaf particles</li>
	 * <li>disable opti-leaves feature</li>
	 * <li>enable light transparency</li>
	 * </ul>
	 *
	 * @param properties The block properties
	 */
	public ExtendedLeavesBlock(BlockBehaviour.Properties properties) {
		this(0.01f, Optional.empty(), false, true, properties);
	}

	public MapCodec<? extends ExtendedLeavesBlock> codec() {
		return CODEC;
	}

	@Override
	protected void spawnFallingLeavesParticle(Level level, BlockPos pos, RandomSource random) {
		ParticleUtils.spawnParticleBelow(level, pos, random, leafParticleOptions
				.orElse(ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, level.getClientLeafTintColor(pos))));
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return getExtendedDistance(state) == MAX_TOTAL_DISTANCE && !state.getValue(PERSISTENT);
	}

	@Override
	public boolean decaying(BlockState state) {
		return !state.getValue(PERSISTENT) && getExtendedDistance(state) == MAX_TOTAL_DISTANCE;
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		level.setBlock(pos, ExtendedLeavesBlock.updateDistance(state, level, pos), 3);
	}

	@Override
	public int getLightDampening(BlockState state) {
		return transparent ? 0 : 1;
	}

	@Override
	public BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
		if (state.getValue(WATERLOGGED)) {
			ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		int distance = ExtendedLeavesBlock.getDistanceAt(neighborState) + 1;
		if (distance != 1 || getExtendedDistance(state) != distance) {
			ticks.scheduleTick(pos, this, 1);
		}

		return state;
	}

	private static BlockState updateDistance(BlockState state, LevelAccessor level, BlockPos pos) {
		int distance = MAX_TOTAL_DISTANCE;
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

		for (Direction direction : Direction.values()) {
			mutable.setWithOffset(pos, direction);
			distance = Math.min(distance, ExtendedLeavesBlock.getDistanceAt(level.getBlockState(mutable)) + 1);
			if (distance == 1) {
				break;
			}
		}

		return setExtendedDistance(state, distance);
	}

	private static int getDistanceAt(BlockState state) {
		return ExtendedLeavesBlock.getOptionalDistanceAt(state).orElse(MAX_TOTAL_DISTANCE);
	}

	/**
	 * <p>
	 * Get an {@link OptionalInt} containing the previously calculated distance from the provided leaves block state
	 * to the nearest log.  The value will be between 0 and 14 inclusive.
	 * </p><p>
	 * A value of 0 indicates the block is a log (including a {@link SmallLogBlock} which may also have leaves).
	 * A value of 14 indicates the leaves should decay and will schedule random ticks.
	 * No value indicates the block state does not contain the {@link LeavesBlock#DISTANCE} property.
	 * </p>
	 *
	 * @param state Target block state for which to fetch extended optional distance
	 * @return OptionalInt of the previously calculated distance, if present
	 */
	public static OptionalInt getOptionalDistanceAt(BlockState state) {
		if (state.is(BlockTags.PREVENTS_NEARBY_LEAF_DECAY)) {
			return OptionalInt.of(0);
		}

		Block block = state.getBlock();
		if (block instanceof ExtendedLeavesBlock) {
			return OptionalInt.of(getExtendedDistance(state));
		} else if (state.hasProperty(DISTANCE)) {
			int distance = state.getValue(DISTANCE);
			return OptionalInt.of(distance < LeavesBlock.DECAY_DISTANCE ? distance : MAX_TOTAL_DISTANCE);
		}

		return OptionalInt.empty();
	}

	/**
	 * <p>
	 * Get the extended distance value for the targeted leaves block state.  It is the caller's responsibility to
	 * determine whether the block state is extended or not.
	 * </p><p>
	 * If the target block state is of {@link ExtendedLeavesBlock} or a descendant, the value will be between 1 and 14
	 * inclusive, and a value of 14 indicates the leaves should decay and will schedule random ticks.
	 * </p><p>
	 * Otherwise, if the target block state contains the {@link LeavesBlock#DISTANCE} property, the value will be
	 * between 1 and 7, and a value of 7 indicates the leaves should decay and will schedule random ticks.
	 * </p>
	 *
	 * @param state Target block state for which to fetch extended distance
	 * @return Extended distance value of the target block state
	 */
	public static int getExtendedDistance(BlockState state) {
		return state.getValue(DISTANCE) + state.getOptionalValue(EXTENDED_DISTANCE).orElse(0);
	}

	/**
	 * <p>
	 * Set the extended distance value for the targeted leaves block state.  It is the caller's responsibility to
	 * determine whether the block state is extended or not.  Passing an out-of-bounds value will throw an exception.
	 * </p><p>
	 * If the target block state is of {@link ExtendedLeavesBlock} or a descendant, the value must be between 1 and 14
	 * inclusive, and a value of 14 indicates the leaves should decay and will schedule random ticks.
	 * </p><p>
	 * Otherwise, if the target block state contains the {@link LeavesBlock#DISTANCE} property, the value must be
	 * between 1 and 7, and a value of 7 indicates the leaves should decay and will schedule random ticks.
	 * </p>
	 *
	 * @param state The target block state for which to update extended distance
	 * @param distance The extended distance to set for the target block state
	 * @return The modified block state
	 */
	public static BlockState setExtendedDistance(BlockState state, int distance) {
		if (state.hasProperty(EXTENDED_DISTANCE)) {
			if (distance > DECAY_DISTANCE) {
				return state.setValue(DISTANCE, DECAY_DISTANCE).setValue(EXTENDED_DISTANCE, distance - DECAY_DISTANCE);
			} else {
				return state.setValue(DISTANCE, distance).setValue(EXTENDED_DISTANCE, 0);
			}
		} else {
			return state.setValue(DISTANCE, distance);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);

		builder.add(EXTENDED_DISTANCE);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
		BlockState blockState = this.defaultBlockState().setValue(PERSISTENT, true).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);

		return ExtendedLeavesBlock.updateDistance(blockState, context.getLevel(), context.getClickedPos());
	}

	@Override
	public boolean skipRendering(BlockState state, BlockState neighborState, Direction direction) {
		// OptiLeaves optimization: Cull faces with identical neighbors to reduce geometry dense forests.
		return opti && neighborState.is(state.getBlock());
	}
}
