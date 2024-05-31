package com.terraformersmc.terraform.dirt.api;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class TerraformDirtBlockTags {
	/**
	 * Dirts, grass blocks, and podzol.
	 */
	public static final TagKey<Block> SOIL = register("soil");
	public static final TagKey<Block> GRASS_BLOCKS = register("grass_blocks");
	public static final TagKey<Block> FARMLAND = register("farmland");

	private TerraformDirtBlockTags() {
		return;
	}

	private static TagKey<Block> register(String id) {
		return TagKey.of(RegistryKeys.BLOCK, Identifier.of("terraform", id));
	}
}
