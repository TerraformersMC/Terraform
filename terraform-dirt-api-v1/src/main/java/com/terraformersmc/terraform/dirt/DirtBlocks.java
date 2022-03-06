package com.terraformersmc.terraform.dirt;

import com.terraformersmc.terraform.dirt.block.TerraformDirtPathBlock;
import com.terraformersmc.terraform.dirt.block.TerraformFarmlandBlock;
import com.terraformersmc.terraform.dirt.block.TerraformGrassBlock;
import com.terraformersmc.terraform.dirt.block.TerraformSnowyBlock;

import net.minecraft.block.Block;

public class DirtBlocks {
	private Block dirt;
	private TerraformGrassBlock grassBlock;
	private TerraformDirtPathBlock dirtPath;
	private TerraformSnowyBlock podzol;
	private TerraformFarmlandBlock farmland;

	private DirtBlocks() {
	}

	/**
	 * Creates a new collection of DirtBlocks. These blocks must have already been registered to the block registry.
	 */
	public DirtBlocks(Block dirt, TerraformGrassBlock grassBlock, TerraformDirtPathBlock dirtPath, TerraformSnowyBlock podzol, TerraformFarmlandBlock farmland) {
		this.dirt = dirt;
		this.grassBlock = grassBlock;
		this.dirtPath = dirtPath;
		this.podzol = podzol;
		this.farmland = farmland;
	}

	public Block getDirt() {
		return dirt;
	}

	public TerraformGrassBlock getGrassBlock() {
		return grassBlock;
	}

	public TerraformDirtPathBlock getDirtPath() {
		return dirtPath;
	}

	public Block getPodzol() {
		return podzol;
	}

	public TerraformFarmlandBlock getFarmland() {
		return farmland;
	}
}
