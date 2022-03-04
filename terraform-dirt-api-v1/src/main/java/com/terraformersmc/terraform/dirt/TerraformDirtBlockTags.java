package com.terraformersmc.terraform.dirt;

import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TerraformDirtBlockTags {
	/**
	 * Dirts, grass blocks, and podzol.
	 */
	public static final TagKey<Block> SOIL = register("soil");
	public static final TagKey<Block> GRASS_BLOCKS = register("grass_blocks");
	public static final TagKey<Block> FARMLAND = register("farmland");

	private TerraformDirtBlockTags() {
	}

	private static TagKey<Block> register(String id) {
		return TagKey.of(Registry.BLOCK_KEY, new Identifier("terraform", id));
	}
}
