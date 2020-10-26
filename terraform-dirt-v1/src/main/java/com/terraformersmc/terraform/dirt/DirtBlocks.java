package com.terraformersmc.terraform.dirt;

import com.terraformersmc.terraform.dirt.block.TerraformFarmlandBlock;
import com.terraformersmc.terraform.dirt.block.TerraformGrassBlock;
import com.terraformersmc.terraform.dirt.block.TerraformGrassPathBlock;
import com.terraformersmc.terraform.dirt.block.TerraformSnowyBlock;

import net.minecraft.block.Block;

public class DirtBlocks {
	private Block dirt;
	private TerraformGrassBlock grassBlock;
	private TerraformGrassPathBlock grassPath;
	private TerraformSnowyBlock podzol;
	private TerraformFarmlandBlock farmland;

	private DirtBlocks() {
	}

	/**
	 * Creates a new collection of DirtBlocks. These blocks must have already been registered to the block registry.
	 */
	public DirtBlocks(Block dirt, TerraformGrassBlock grassBlock, TerraformGrassPathBlock grassPath, TerraformSnowyBlock podzol, TerraformFarmlandBlock farmland) {
		this.dirt = dirt;
		this.grassBlock = grassBlock;
		this.grassPath = grassPath;
		this.podzol = podzol;
		this.farmland = farmland;
	}

	public Block getDirt() {
		return dirt;
	}

	public TerraformGrassBlock getGrassBlock() {
		return grassBlock;
	}

	public TerraformGrassPathBlock getGrassPath() {
		return grassPath;
	}

	public Block getPodzol() {
		return podzol;
	}

	public TerraformFarmlandBlock getFarmland() {
		return farmland;
	}
}
