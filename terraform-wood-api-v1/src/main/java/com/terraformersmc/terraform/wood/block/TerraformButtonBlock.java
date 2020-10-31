package com.terraformersmc.terraform.wood.block;

import net.minecraft.block.Block;
import net.minecraft.block.WoodenButtonBlock;

/**
 * A simple wrapper around the parent WoodButtonBlock class allowing access to the constructor.
 */
public class TerraformButtonBlock extends WoodenButtonBlock {
	public TerraformButtonBlock(Block.Settings settings) {
		super(settings);
	}
}
