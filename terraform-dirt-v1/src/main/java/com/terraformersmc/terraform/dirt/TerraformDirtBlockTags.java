package com.terraformersmc.terraform.dirt;

import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.tag.TagRegistry;

public class TerraformDirtBlockTags {
	public static final Tag<Block> FARMLAND = register("farmland");

	private TerraformDirtBlockTags() {
	}

	private static Tag<Block> register(String id) {
		return TagRegistry.block(new Identifier("terraform", id));
	}
}
