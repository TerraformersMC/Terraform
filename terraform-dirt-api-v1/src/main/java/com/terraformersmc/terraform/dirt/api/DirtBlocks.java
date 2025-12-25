package com.terraformersmc.terraform.dirt.api;

import com.terraformersmc.terraform.dirt.api.block.TerraformDirtPathBlock;
import com.terraformersmc.terraform.dirt.api.block.TerraformFarmBlock;
import com.terraformersmc.terraform.dirt.api.block.TerraformGrassBlock;
import com.terraformersmc.terraform.dirt.api.block.TerraformSnowyDirtBlock;
import net.minecraft.world.level.block.Block;

public class DirtBlocks {
	private Block dirtBlock;
	private TerraformGrassBlock grassBlock;
	private TerraformDirtPathBlock dirtPathBlock;
	private TerraformSnowyDirtBlock podzolBlock;
	private TerraformFarmBlock farmBlock;

	private DirtBlocks() {
		return;
	}

	/**
	 * Creates a new collection of DirtBlocks. These blocks must have already been registered to the block registry.
	 */
	public DirtBlocks(Block dirtBlock, TerraformGrassBlock grassBlock, TerraformDirtPathBlock dirtPathBlock, TerraformSnowyDirtBlock podzolBlock, TerraformFarmBlock farmBlock) {
		this.dirtBlock = dirtBlock;
		this.grassBlock = grassBlock;
		this.dirtPathBlock = dirtPathBlock;
		this.podzolBlock = podzolBlock;
		this.farmBlock = farmBlock;
	}

	public Block getDirtBlock() {
		return dirtBlock;
	}

	public TerraformGrassBlock getGrassBlock() {
		return grassBlock;
	}

	public TerraformDirtPathBlock getDirtPathBlock() {
		return dirtPathBlock;
	}

	public Block getPodzolBlock() {
		return podzolBlock;
	}

	public TerraformFarmBlock getFarmBlock() {
		return farmBlock;
	}
}
