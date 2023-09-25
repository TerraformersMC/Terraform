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
	private static final Map<Block, DirtBlocks> BY_FARMLAND = new HashMap<>();

	/**
	 * Registers a new set of dirt blocks to Terraform.
	 *
	 * <p>This is needed to make sure that the dirt can be tilled into farmland, that plants and saplings will work with
	 * your dirt, and make most other interactions work.</p>
	 *
	 * <p>Please note that you must add your blocks to the correct {@link TerraformDirtBlockTags tags}</p> as well, or
	 * else things will not work properly!</p>
	 *
	 * @param blocks the DirtBlocks to register with Terraform. Note that you are still responsible for registering the
	 *               block instances with {@link net.minecraft.registry.Registries#BLOCK} yourself, this method does
	 *               not do that for you.
	 * @return the registered DirtBlocks instance for convenience
	 */
	@SuppressWarnings("unused")
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

    /**
     * Return the corresponding DirtBlocks if the Block at pos is any Terraform API dirt variant;
	 * otherwise returns {@link Optional#empty()}.
	 *
	 * <p>If you are looking for only specific dirt variants, see
	 * {@link TerraformDirtRegistry#getByGrassBlock(Block)} and
	 * {@link TerraformDirtRegistry#getByFarmland(Block)}.</p>
     */
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
