package com.terraformersmc.terraform.leaves.api.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.EntityEffectParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.ParticleUtil;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

import java.util.Optional;

/**
 * A leaves block with extended range, permitting leaves to be as far as 13 blocks away from the tree rather than the
 * limit of 6 blocks imposed by vanilla leaves.  Enabling the opti-leaves feature can reduce geometry and give better
 * rendering performance in dense forests of large trees.  By default, extended leaves are transparent (do not block
 * light at all).  If the optional leaf_particle field is present, the custom particle is used; otherwise a tinted
 * falling leaf particle is used.
 */
/* This class must override every LeavesBlock function that references (compiler inlined) MAX_DISTANCE.
 * The DISTANCE_1_7 property used by LeavesBlock is complemented by our EXTENDED_DISTANCE property.
 */
@SuppressWarnings({"unused", "OptionalUsedAsFieldOrParameterType"})
public class ExtendedLeavesBlock extends LeavesBlock {
	public static final MapCodec<ExtendedLeavesBlock> CODEC = RecordCodecBuilder.mapCodec(
			(instance) -> instance.group(
							Codecs.rangedInclusiveFloat(0.0f, 1.0f).fieldOf("leaf_particle_chance")
									.forGetter(arg -> arg.leafParticleChance),
							ParticleTypes.TYPE_CODEC.optionalFieldOf("leaf_particle")
									.forGetter(arg -> arg.leafParticleEffect),
							Codec.BOOL.fieldOf("opti")
									.forGetter(arg -> arg.opti),
							Codec.BOOL.fieldOf("transparent")
									.forGetter(arg -> arg.transparent),
							ExtendedLeavesBlock.createSettingsCodec()
					)
					.apply(instance, ExtendedLeavesBlock::new));

	public static final int MAX_EXTENDED_DISTANCE = 7;
	public static final int MAX_TOTAL_DISTANCE = MAX_DISTANCE + MAX_EXTENDED_DISTANCE;
	public static final IntProperty EXTENDED_DISTANCE = IntProperty.of("extended_distance", 0, MAX_EXTENDED_DISTANCE);

	protected final Optional<ParticleEffect> leafParticleEffect;
	protected final boolean opti;
	protected final boolean transparent;

	/**
	 * Full options constructor for Terraform ExtendedLeaves.
	 *
	 * @param leafParticleChance The relative likelihood of each leaf block spawning leaf particles
	 * @param leafParticleEffect Optional {@link ParticleEffect} to use instead of biome tinted falling leaf particles
	 * @param opti Whether to enable the opti-leaves feature
	 * @param transparent Whether to allow light to pass freely through the block
	 * @param settings The block settings
	 */
	public ExtendedLeavesBlock(float leafParticleChance, Optional<ParticleEffect> leafParticleEffect, boolean opti, boolean transparent, AbstractBlock.Settings settings) {
		super(leafParticleChance, settings);

		this.leafParticleEffect = leafParticleEffect;
		this.opti = opti;
		this.transparent = transparent;

		this.setDefaultState(this.stateManager.getDefaultState()
				.with(DISTANCE, MAX_DISTANCE)
				.with(EXTENDED_DISTANCE, MAX_EXTENDED_DISTANCE)
				.with(PERSISTENT, false)
				.with(WATERLOGGED, false));
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
	 * @param settings The block settings
	 */
	public ExtendedLeavesBlock(AbstractBlock.Settings settings) {
		this(0.01f, Optional.empty(), false, true, settings);
	}

	public MapCodec<? extends ExtendedLeavesBlock> getCodec() {
		return CODEC;
	}

	@Override
	protected void spawnLeafParticle(World world, BlockPos pos, Random random) {
		ParticleUtil.spawnParticle(world, pos, random, leafParticleEffect
				.orElse(EntityEffectParticleEffect.create(ParticleTypes.TINTED_LEAVES, world.getBlockColor(pos))));
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return getExtendedDistance(state) == MAX_TOTAL_DISTANCE && !state.get(PERSISTENT);
	}

	@Override
	public boolean shouldDecay(BlockState state) {
		return !state.get(PERSISTENT) && getExtendedDistance(state) == MAX_TOTAL_DISTANCE;
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		world.setBlockState(pos, ExtendedLeavesBlock.updateDistanceFromLogs(state, world, pos), 3);
	}

	@Override
	public int getOpacity(BlockState state) {
		return transparent ? 0 : 1;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
		if (state.get(WATERLOGGED)) {
			tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}

		int distance = ExtendedLeavesBlock.getDistanceFromLog(neighborState) + 1;
		if (distance != 1 || getExtendedDistance(state) != distance) {
			tickView.scheduleBlockTick(pos, this, 1);
		}

		return state;
	}

	private static BlockState updateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
		int distance = MAX_TOTAL_DISTANCE;
		BlockPos.Mutable mutable = new BlockPos.Mutable();

		for (Direction direction : Direction.values()) {
			mutable.set(pos, direction);
			distance = Math.min(distance, ExtendedLeavesBlock.getDistanceFromLog(world.getBlockState(mutable)) + 1);
			if (distance == 1) {
				break;
			}
		}

		if (distance > MAX_DISTANCE) {
			return state.with(DISTANCE, MAX_DISTANCE).with(EXTENDED_DISTANCE, distance - MAX_DISTANCE);
		} else {
			return state.with(DISTANCE, distance).with(EXTENDED_DISTANCE, 0);
		}
	}

	private static int getDistanceFromLog(BlockState state) {
		if (state.isIn(BlockTags.LOGS)) {
			return 0;
		}

		Block block = state.getBlock();
		if (block instanceof ExtendedLeavesBlock) {
			return getExtendedDistance(state);
		} else if (block instanceof LeavesBlock) {
			int distance = state.get(DISTANCE);
			return distance < LeavesBlock.MAX_DISTANCE ? distance : MAX_TOTAL_DISTANCE;
		}

		return MAX_TOTAL_DISTANCE;
	}

	private static int getExtendedDistance(BlockState state) {
		return state.get(DISTANCE) + state.get(EXTENDED_DISTANCE);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);

		builder.add(EXTENDED_DISTANCE);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		FluidState fluidState = context.getWorld().getFluidState(context.getBlockPos());
		BlockState blockState = this.getDefaultState().with(PERSISTENT, true).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);

		return ExtendedLeavesBlock.updateDistanceFromLogs(blockState, context.getWorld(), context.getBlockPos());
	}

	@Override
	public boolean isSideInvisible(BlockState state, BlockState neighborState, Direction offset) {
		// OptiLeaves optimization: Cull faces with identical neighbors to reduce geometry dense forests.
		return opti && neighborState.isOf(state.getBlock());
	}
}
