package com.terraformersmc.terraform.dirt;

import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.tag.TagFactory;

public class TerraformDirtBlockTags {
	/**
	 * Dirts, grass blocks, and podzol.
	 */
	public static final Tag<Block> SOIL = register("soil");
	public static final Tag<Block> GRASS_BLOCKS = register("grass_blocks");
	public static final Tag<Block> FARMLAND = register("farmland");

	private TerraformDirtBlockTags() {
	}

	private static Tag<Block> register(String id) {
		return TagFactory.BLOCK.create(new Identifier("terraform", id));
	}
}
