package com.terraformersmc.terraform.dirt.api;

import com.terraformersmc.terraform.dirt.api.block.TerraformDirtPathBlock;
import com.terraformersmc.terraform.dirt.api.block.TerraformFarmlandBlock;
import com.terraformersmc.terraform.dirt.api.block.TerraformGrassBlock;
import com.terraformersmc.terraform.dirt.api.block.TerraformSnowyBlock;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

/**
 * Stores a collection of DirtBlocks.
 * These blocks must already be registered to the block registry.
 *
 * @param dirtBlock mandatory Block of the base dirt
 * @param grassBlock optional TerraformGrassBlock of the grassy dirt
 * @param dirtPathBlock optional TerraformDirtPathBlock of trampled path
 * @param podzolBlock  optional TerraformSnowyBlock of the podzol form
 * @param farmBlock optional TerraformFarmlandBlock of the cultivated dirt
 */
@SuppressWarnings("unused")
public record DirtBlocks(
	Block dirtBlock,
	@Nullable TerraformGrassBlock grassBlock,
	@Nullable TerraformDirtPathBlock dirtPathBlock,
	@Nullable TerraformSnowyBlock podzolBlock,
	@Nullable TerraformFarmlandBlock farmBlock
) {
}
