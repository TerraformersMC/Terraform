package com.terraformersmc.terraform.dirt.api.registry;

import java.util.Optional;

import com.terraformersmc.terraform.dirt.api.DirtBlocks;
import com.terraformersmc.terraform.dirt.api.TerraformDirtBlockTags;
import com.terraformersmc.terraform.dirt.impl.registry.TerraformDirtRegistryImpl;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;

public final class TerraformDirtRegistry {
	private TerraformDirtRegistry() {
		return;
	}

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
		return TerraformDirtRegistryImpl.register(blocks);
	}

    /**
     * Return the corresponding DirtBlocks if the Block at pos is any Terraform API dirt variant;
	 * otherwise returns {@link Optional#empty()}.
	 *
	 * <p>If you are looking for only specific dirt variants, see
	 * {@link TerraformDirtRegistry#getByGrassBlock(Block)} and
	 * {@link TerraformDirtRegistry#getByFarmland(Block)}.</p>
	 *
	 * @param world the world to check
	 * @param pos the block position in the world to check
	 * @return optional of the matching {@link DirtBlocks} if any
     */
    public static Optional<DirtBlocks> getFromWorld(TestableWorld world, BlockPos pos) {
		return TerraformDirtRegistryImpl.getFromWorld(world, pos);
	}

	/**
	 * Return the corresponding DirtBlocks if the given grass block is any Terraform API dirt variant;
	 * otherwise returns {@link Optional#empty()}.
	 *
	 * @param grass the grass block to check
	 * @return optional of the matching {@link DirtBlocks} if any
	 */
	public static Optional<DirtBlocks> getByGrassBlock(Block grass) {
		return TerraformDirtRegistryImpl.getByGrassBlock(grass);
	}

	/**
	 * Return the corresponding DirtBlocks if the given farmland is any Terraform API dirt variant;
	 * otherwise returns {@link Optional#empty()}.
	 *
	 * @param farmland the farmland block to check
	 * @return optional of the matching {@link DirtBlocks} if any
	 */
	public static Optional<DirtBlocks> getByFarmland(Block farmland) {
		return TerraformDirtRegistryImpl.getByFarmland(farmland);
	}
}
