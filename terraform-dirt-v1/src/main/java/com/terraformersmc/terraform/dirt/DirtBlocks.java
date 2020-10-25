package com.terraformersmc.terraform.dirt;

import com.terraformersmc.terraform.dirt.block.TerraformFarmlandBlock;
import com.terraformersmc.terraform.dirt.block.TerraformGrassBlock;
import com.terraformersmc.terraform.dirt.block.TerraformGrassPathBlock;
import com.terraformersmc.terraform.dirt.block.TerraformSnowyBlock;

import net.minecraft.block.Block;

public class DirtBlocks {
	/*
	ANDISOL = TerrestriaRegistry.register("basalt_dirt", new Block(FabricBlockSettings.copyOf(Blocks.DIRT).materialColor(MaterialColor.BLACK).breakByTool(FabricToolTags.SHOVELS, 0)));
		ANDISOL_GRASS_BLOCK = TerrestriaRegistry.register("basalt_grass_block", new BasaltGrassBlock(ANDISOL, () -> ANDISOL_GRASS_PATH, FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).breakByTool(FabricToolTags.SHOVELS, 0)));
		ANDISOL_GRASS_PATH = TerrestriaRegistry.register("basalt_grass_path", new TerraformGrassPathBlock(ANDISOL, FabricBlockSettings.copyOf(Blocks.GRASS_PATH).breakByTool(FabricToolTags.SHOVELS, 0)));
		ANDISOL_PODZOL = TerrestriaRegistry.register("basalt_podzol", new TerraformSnowyBlock(FabricBlockSettings.copyOf(Blocks.PODZOL).breakByTool(FabricToolTags.SHOVELS, 0)));
		ANDISOL_FARMLAND = TerrestriaRegistry.register("andisol_farmland", new TerraformFarmlandBlock(FabricBlockSettings.copyOf(Blocks.FARMLAND).materialColor(MaterialColor.BLACK).breakByTool(FabricToolTags.SHOVELS, 0), ANDISOL));
	 */

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

	/*public static void register(MaterialColor baseColor, Identifier dirt, Identifier grassBlock, Identifier grassPath, Identifier podzol, Identifier farmland) {
		DirtBlocks blocks = new DirtBlocks();

		blocks.dirt = Registry.register(Registry.BLOCK, dirt, new Block(FabricBlockSettings.copyOf(Blocks.DIRT).materialColor(MaterialColor.BLACK).breakByTool(FabricToolTags.SHOVELS, 0)));
	}*/

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
