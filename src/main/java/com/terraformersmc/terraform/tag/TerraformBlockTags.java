package com.terraformersmc.terraform.tag;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.tag.TagRegistry;

public class TerraformBlockTags {
	public static final Tag<Block> FARMLAND = register("farmland");

	private TerraformBlockTags() { }

	private static Tag<Block> register(String id) {
		return TagRegistry.block(new Identifier("terraform", id));
	}
}
