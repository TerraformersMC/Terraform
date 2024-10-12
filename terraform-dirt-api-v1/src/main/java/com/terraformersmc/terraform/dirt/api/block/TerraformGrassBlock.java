package com.terraformersmc.terraform.dirt.api.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.*;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * A custom grass block that allows one to define their own soil types, used for things like basalt grass.
 */
public class TerraformGrassBlock extends GrassBlock {
	private final Block dirt;
	private final Map<Block, Block> spreadsTo;
	private final Supplier<Block> path;
	public static final Map<Block, Block> GRASS_SPREADS_TO = new HashMap<>();
	private static final int MAX_LIGHT_LEVEL = 15;

	public TerraformGrassBlock(Block dirt, Supplier<Block> path, Block.Settings settings) {
		this(dirt, path, settings, ImmutableMap.of(Blocks.DIRT, Blocks.GRASS_BLOCK));
	}

	/**
	 * @param dirt      The dirt block that this block turns back to when it loses its grass
	 * @param spreadsTo Maps dirt blocks to the grass they turn into when spreaded to.
	 */
	public TerraformGrassBlock(Block dirt, Supplier<Block> path, Block.Settings settings, Map<Block, Block> spreadsTo) {
		this(dirt, path, settings, spreadsTo, true);
	}

	/**
	 * @param dirt           The dirt block that this block turns back to when it loses its grass
	 * @param spreadsTo      Maps dirt blocks to the grass they turn into when spreaded to.
	 * @param grassSpreadsTo If true, grass will spread to the block specified in the 'dirt' parameter, turning into this block
	 */
	public TerraformGrassBlock(Block dirt, Supplier<Block> path, Block.Settings settings, Map<Block, Block> spreadsTo, boolean grassSpreadsTo) {
		super(settings);
		this.dirt = dirt;
		this.spreadsTo = spreadsTo;
		this.path = path;
		if (grassSpreadsTo) {
			GRASS_SPREADS_TO.put(dirt, this);
		}
	}

	private static boolean canSurvive(BlockState state, WorldView world, BlockPos pos) {
		BlockPos above = pos.up();
		BlockState aboveState = world.getBlockState(above);

		if (aboveState.isOf(Blocks.SNOW) && aboveState.get(SnowBlock.LAYERS) == 1) {
			return true;
		} else if (aboveState.getFluidState().getLevel() == 8) {
			return false;
		} else {
			int lightingAt = ChunkLightProvider.getRealisticOpacity(state, aboveState, Direction.UP, aboveState.getOpacity());
			return lightingAt < MAX_LIGHT_LEVEL;
		}
	}

	public static boolean canSpread(BlockState state, WorldView world, BlockPos pos) {
		BlockPos above = pos.up();
		return canSurvive(state, world, pos) && !world.getFluidState(above).isIn(FluidTags.WATER);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!world.isClient) {
			if (!canSurvive(state, world, pos)) {
				world.setBlockState(pos, dirt.getDefaultState());
			} else if (world.getLightLevel(LightType.SKY, pos.up()) >= 4) {
				if (world.getLightLevel(LightType.SKY, pos.up()) >= 9) {
					BlockState defaultState = this.getDefaultState();

					for (int int_1 = 0; int_1 < 4; ++int_1) {
						BlockPos spreadingPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);

						Block spreadTarget = world.getBlockState(spreadingPos).getBlock();
						if (spreadTarget == dirt && canSpread(defaultState, world, spreadingPos)) {
							world.setBlockState(spreadingPos, defaultState.with(SNOWY, world.getBlockState(spreadingPos.up()).getBlock() == Blocks.SNOW));
						}
						Block spreadedBlock = spreadsTo.get(spreadTarget);
						if (spreadedBlock != null && canSpread(defaultState, world, spreadingPos)) {
							BlockState spreadedState = spreadedBlock.getDefaultState();
							if (spreadedBlock instanceof SnowyBlock) {
								spreadedState = spreadedState.with(SNOWY, world.getBlockState(spreadingPos.up()).getBlock() == Blocks.SNOW);
							}
							world.setBlockState(spreadingPos, spreadedState);
						}
					}
				}

			}
		}
	}
}
