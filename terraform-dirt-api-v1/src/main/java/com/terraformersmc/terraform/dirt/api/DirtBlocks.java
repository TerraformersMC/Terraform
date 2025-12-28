package com.terraformersmc.terraform.dirt.api;

import com.terraformersmc.terraform.dirt.api.block.TerraformDirtPathBlock;
import com.terraformersmc.terraform.dirt.api.block.TerraformFarmBlock;
import com.terraformersmc.terraform.dirt.api.block.TerraformGrassBlock;
import com.terraformersmc.terraform.dirt.api.block.TerraformSnowyDirtBlock;
import net.minecraft.world.level.block.Block;

/**
 * Stores a collection of DirtBlocks. These blocks must have already been registered to the block registry.
 */
@SuppressWarnings("unused")
public record DirtBlocks(
	Block dirtBlock,
	TerraformGrassBlock grassBlock,
	TerraformDirtPathBlock dirtPathBlock,
	TerraformSnowyDirtBlock podzolBlock,
	TerraformFarmBlock farmBlock
) {
}
