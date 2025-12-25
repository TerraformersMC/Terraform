package com.terraformersmc.terraform.dirt.api.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;
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

	public TerraformGrassBlock(Block dirt, Supplier<Block> path, BlockBehaviour.Properties settings) {
		this(dirt, path, settings, ImmutableMap.of(Blocks.DIRT, Blocks.GRASS_BLOCK));
	}

	/**
	 * @param dirt      The dirt block that this block turns back to when it loses its grass
	 * @param spreadsTo Maps dirt blocks to the grass they turn into when spreaded to.
	 */
	public TerraformGrassBlock(Block dirt, Supplier<Block> path, BlockBehaviour.Properties settings, Map<Block, Block> spreadsTo) {
		this(dirt, path, settings, spreadsTo, true);
	}

	/**
	 * @param dirt           The dirt block that this block turns back to when it loses its grass
	 * @param spreadsTo      Maps dirt blocks to the grass they turn into when spreaded to.
	 * @param grassSpreadsTo If true, grass will spread to the block specified in the 'dirt' parameter, turning into this block
	 */
	public TerraformGrassBlock(Block dirt, Supplier<Block> path, BlockBehaviour.Properties settings, Map<Block, Block> spreadsTo, boolean grassSpreadsTo) {
		super(settings);
		this.dirt = dirt;
		this.spreadsTo = spreadsTo;
		this.path = path;
		if (grassSpreadsTo) {
			GRASS_SPREADS_TO.put(dirt, this);
		}
	}

	private static boolean canBeGrass(BlockState state, LevelReader world, BlockPos pos) {
		BlockPos above = pos.above();
		BlockState aboveState = world.getBlockState(above);

		if (aboveState.is(Blocks.SNOW) && aboveState.getValue(SnowLayerBlock.LAYERS) == 1) {
			return true;
		} else if (aboveState.getFluidState().getAmount() == 8) {
			return false;
		} else {
			int lightingAt = LightEngine.getLightBlockInto(state, aboveState, Direction.UP, aboveState.getLightBlock());
			return lightingAt < MAX_LIGHT_LEVEL;
		}
	}

	public static boolean canPropagate(BlockState state, LevelReader world, BlockPos pos) {
		BlockPos above = pos.above();
		return canBeGrass(state, world, pos) && !world.getFluidState(above).is(FluidTags.WATER);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		if (world.isClientSide()) {
			return;
		}

		if (!canBeGrass(state, world, pos)) {
			world.setBlockAndUpdate(pos, dirt.defaultBlockState());
		} else if (world.getBrightness(LightLayer.SKY, pos.above()) >= 4) {
			if (world.getBrightness(LightLayer.SKY, pos.above()) >= 9) {
				BlockState defaultState = this.defaultBlockState();

				for (int int_1 = 0; int_1 < 4; ++int_1) {
					BlockPos spreadingPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);

					Block spreadTarget = world.getBlockState(spreadingPos).getBlock();
					if (spreadTarget == dirt && canPropagate(defaultState, world, spreadingPos)) {
						world.setBlockAndUpdate(spreadingPos, defaultState.setValue(SNOWY, world.getBlockState(spreadingPos.above()).getBlock() == Blocks.SNOW));
					}
					Block spreadedBlock = spreadsTo.get(spreadTarget);
					if (spreadedBlock != null && canPropagate(defaultState, world, spreadingPos)) {
						BlockState spreadedState = spreadedBlock.defaultBlockState();
						if (spreadedBlock instanceof SnowyDirtBlock) {
							spreadedState = spreadedState.setValue(SNOWY, world.getBlockState(spreadingPos.above()).getBlock() == Blocks.SNOW);
						}
						world.setBlockAndUpdate(spreadingPos, spreadedState);
					}
				}
			}
		}
	}
}
