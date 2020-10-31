package com.terraformersmc.terraform.wood.block;

import net.minecraft.block.TrapdoorBlock;

/**
 * A simple wrapper around the parent TrapdoorBlock class allowing access to the constructor.
 */
public class TerraformTrapdoorBlock extends TrapdoorBlock {
	public TerraformTrapdoorBlock(Settings settings) {
		super(settings);
	}
}
