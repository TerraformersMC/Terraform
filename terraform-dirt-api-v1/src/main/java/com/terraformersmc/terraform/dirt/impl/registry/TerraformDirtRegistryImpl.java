package com.terraformersmc.terraform.dirt.impl.registry;

import com.terraformersmc.terraform.dirt.api.DirtBlocks;
import com.terraformersmc.terraform.dirt.api.registry.TillableBlockRegistry;
import java.util.*;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TerraformDirtRegistryImpl {
	private static final List<DirtBlocks> TYPES = new ArrayList<>();
	private static final Map<Block, DirtBlocks> BY_GRASS_BLOCK = new HashMap<>();
	private static final Map<Block, DirtBlocks> BY_FARM_BLOCK = new HashMap<>();

	public static DirtBlocks register(DirtBlocks blocks) {
		Objects.requireNonNull(blocks);

		TYPES.add(blocks);
		BY_GRASS_BLOCK.put(blocks.grassBlock(), blocks);
		BY_FARM_BLOCK.put(blocks.farmBlock(), blocks);

		TillableBlockRegistry.add(blocks.dirtBlock(), blocks.farmBlock().defaultBlockState());
		TillableBlockRegistry.add(blocks.grassBlock(), blocks.farmBlock().defaultBlockState());
		TillableBlockRegistry.add(blocks.dirtPathBlock(), blocks.farmBlock().defaultBlockState());

		return blocks;
	}

    public static Optional<DirtBlocks> getFromLevel(LevelSimulatedReader level, BlockPos pos) {
		for (DirtBlocks blocks: TYPES) {
			Predicate<BlockState> isDirtLike =
					state -> state.is(blocks.dirtBlock()) ||
							state.is(blocks.dirtPathBlock()) ||
							state.is(blocks.farmBlock()) ||
							state.is(blocks.grassBlock()) ||
							state.is(blocks.podzolBlock());

			if (level.isStateAtPosition(pos, isDirtLike)) {
				return Optional.of(blocks);
			}
		}

		return Optional.empty();
	}

	public static Optional<DirtBlocks> getByGrassBlock(Block grassBlock) {
		return Optional.ofNullable(BY_GRASS_BLOCK.get(grassBlock));
	}

	public static Optional<DirtBlocks> getByFarmBlock(Block farmBlock) {
		return Optional.ofNullable(BY_FARM_BLOCK.get(farmBlock));
	}
}
