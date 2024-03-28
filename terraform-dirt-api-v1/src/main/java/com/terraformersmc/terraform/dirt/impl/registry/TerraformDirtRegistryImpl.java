package com.terraformersmc.terraform.dirt.impl.registry;

import com.terraformersmc.terraform.dirt.api.DirtBlocks;
import com.terraformersmc.terraform.dirt.api.registry.TillableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;

import java.util.*;
import java.util.function.Predicate;

public class TerraformDirtRegistryImpl {
	private static final List<DirtBlocks> TYPES = new ArrayList<>();
	private static final Map<Block, DirtBlocks> BY_GRASS_BLOCK = new HashMap<>();
	private static final Map<Block, DirtBlocks> BY_FARMLAND = new HashMap<>();

	public static DirtBlocks register(DirtBlocks blocks) {
		Objects.requireNonNull(blocks);

		TYPES.add(blocks);
		BY_GRASS_BLOCK.put(blocks.getGrassBlock(), blocks);
		BY_FARMLAND.put(blocks.getFarmland(), blocks);

		TillableBlockRegistry.add(blocks.getDirt(), blocks.getFarmland().getDefaultState());
		TillableBlockRegistry.add(blocks.getGrassBlock(), blocks.getFarmland().getDefaultState());
		TillableBlockRegistry.add(blocks.getDirtPath(), blocks.getFarmland().getDefaultState());

		return blocks;
	}

    public static Optional<DirtBlocks> getFromWorld(TestableWorld world, BlockPos pos) {
		for (DirtBlocks blocks: TYPES) {
			Predicate<BlockState> isDirtLike =
					state -> state.isOf(blocks.getDirt()) ||
							state.isOf(blocks.getDirtPath()) ||
							state.isOf(blocks.getFarmland()) ||
							state.isOf(blocks.getGrassBlock()) ||
							state.isOf(blocks.getPodzol());

			if (world.testBlockState(pos, isDirtLike)) {
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
