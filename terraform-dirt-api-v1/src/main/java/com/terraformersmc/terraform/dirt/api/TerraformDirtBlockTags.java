package com.terraformersmc.terraform.dirt.api;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

@SuppressWarnings("unused")
public final class TerraformDirtBlockTags {
	/**
	 * Dirts, grass blocks, and podzol.
	 */
	public static final TagKey<Block> SOIL = register("soil");
	public static final TagKey<Block> GRASS_BLOCKS = register("grass_blocks");
	public static final TagKey<Block> FARMLAND = register("farmland");

	@SuppressWarnings("UnnecessaryReturnStatement")
	private TerraformDirtBlockTags() {
		return;
	}

	private static TagKey<Block> register(String name) {
		return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("terraform", name));
	}
}
