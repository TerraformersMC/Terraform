package com.terraformersmc.terraform.dirt.impl.registry;

import com.terraformersmc.terraform.dirt.api.DirtBlocks;

import java.util.*;
import java.util.function.Predicate;

import net.fabricmc.fabric.api.item.v1.BlockTransformerHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class TerraformDirtRegistryImpl {
	private static final List<DirtBlocks> TYPES = new ArrayList<>();
	private static final Map<Block, DirtBlocks> BY_GRASS_BLOCK = new HashMap<>();
	private static final Map<Block, DirtBlocks> BY_FARM_BLOCK = new HashMap<>();

	public static DirtBlocks register(DirtBlocks blocks) {
		Objects.requireNonNull(blocks);

		TYPES.add(blocks);
		if (blocks.grassBlock() != null) {
			BY_GRASS_BLOCK.put(blocks.grassBlock(), blocks);
		}
		if (blocks.farmBlock() != null) {
			BY_FARM_BLOCK.put(blocks.farmBlock(), blocks);
		}

		// Shovel transitions to path
		if (blocks.pathBlock() != null) {
			BlockTransformerHelper.registerFlattening(blocks.turnsIntoDirtPath(), blocks.pathBlock());
		}

		// Hoe transition coarse dirt -> dirt
		if (blocks.coarseDirtBlock() != null) {
			BlockTransformerHelper.registerTilling(blocks.coarseDirtBlock(), blocks.dirtBlock());
		}

		// Hoe transitions to farm block
		if (blocks.farmBlock() != null) {
			BlockTransformerHelper.registerTilling(blocks.turnsIntoFarmland(), blocks.farmBlock());
		}

		// Hoe transition rooted dirt -> dirt
		if (blocks.rootedDirtBlock() != null) {
			BlockTransformerHelper.registerHoe(
				BlockTransformer.BlockTransformData.builder(
					BlockPredicate.matchesBlocks(blocks.rootedDirtBlock()),
					blocks.dirtBlock()
				)
					.sound(SoundEvents.HOE_TILL)
					.loot(BuiltInLootTables.TILL_ROOTED_DIRT)
					.dropStrategy(BlockTransformer.DropStrategy.CLICKED_FACE)
					.build()
			);
		}

		return blocks;
	}


    public static Optional<DirtBlocks> getFromLevel(LevelSimulatedReader level, BlockPos pos) {
		for (DirtBlocks blocks: TYPES) {
			// TypedInstance.is(T) is technically null safe
			//noinspection ConstantConditions
			Predicate<BlockState> isDirtLike =
					state ->
								state.is(blocks.dirtBlock()) ||
								state.is(blocks.coarseDirtBlock()) ||
								state.is(blocks.farmBlock()) ||
								state.is(blocks.grassBlock()) ||
								state.is(blocks.myceliumBlock()) ||
								state.is(blocks.pathBlock()) ||
								state.is(blocks.podzolBlock()) ||
								state.is(blocks.rootedDirtBlock());

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
