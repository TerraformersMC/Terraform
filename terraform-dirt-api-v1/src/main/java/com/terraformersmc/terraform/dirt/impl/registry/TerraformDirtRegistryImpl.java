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
	private static final Map<Block, DirtBlocks> BY_FARMLAND = new HashMap<>();

	public static DirtBlocks register(DirtBlocks blocks) {
		Objects.requireNonNull(blocks);

		TYPES.add(blocks);
		BY_GRASS_BLOCK.put(blocks.getGrassBlock(), blocks);
		BY_FARMLAND.put(blocks.getFarmland(), blocks);

		TillableBlockRegistry.add(blocks.getDirt(), blocks.getFarmland().defaultBlockState());
		TillableBlockRegistry.add(blocks.getGrassBlock(), blocks.getFarmland().defaultBlockState());
		TillableBlockRegistry.add(blocks.getDirtPath(), blocks.getFarmland().defaultBlockState());

		return blocks;
	}

    public static Optional<DirtBlocks> getFromWorld(LevelSimulatedReader world, BlockPos pos) {
		for (DirtBlocks blocks: TYPES) {
			Predicate<BlockState> isDirtLike =
					state -> state.is(blocks.getDirt()) ||
							state.is(blocks.getDirtPath()) ||
							state.is(blocks.getFarmland()) ||
							state.is(blocks.getGrassBlock()) ||
							state.is(blocks.getPodzol());

			if (world.isStateAtPosition(pos, isDirtLike)) {
				return Optional.of(blocks);
			}
		}

		return Optional.empty();
	}

	public static Optional<DirtBlocks> getByGrassBlock(Block grass) {
		return Optional.ofNullable(BY_GRASS_BLOCK.get(grass));
	}

	public static Optional<DirtBlocks> getByFarmland(Block farmland) {
		return Optional.ofNullable(BY_FARMLAND.get(farmland));
	}
}
