package com.terraformersmc.terraform.dirt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;

public class TerraformDirtRegistry {
	private static final List<DirtBlocks> TYPES = new ArrayList<>();
	private static final Map<Block, DirtBlocks> BY_GRASS_BLOCK = new HashMap<>();

	public static DirtBlocks register(DirtBlocks blocks) {
		Objects.requireNonNull(blocks);

		TYPES.add(blocks);
		BY_GRASS_BLOCK.put(blocks.getGrassBlock(), blocks);

		return blocks;
	}

	public static Optional<DirtBlocks> getFromWorld(TestableWorld world, BlockPos pos) {
		for (DirtBlocks blocks: TYPES) {
			Predicate<BlockState> isDirtLike =
					state -> state.getBlock() == blocks.getDirt() ||
							state.getBlock() == blocks.getGrassBlock() ||
							state.getBlock() == blocks.getPodzol();

			if (world.testBlockState(pos, isDirtLike)) {
				return Optional.of(blocks);
			}
		}

		return Optional.empty();
	}

	public static Optional<DirtBlocks> getByGrassBlock(Block grass) {
		return Optional.ofNullable(BY_GRASS_BLOCK.get(grass));
	}
}
