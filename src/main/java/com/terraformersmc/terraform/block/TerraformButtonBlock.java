package com.terraformersmc.terraform.block;

import net.minecraft.block.Block;
import net.minecraft.block.WoodButtonBlock;

/**
 * A simple wrapper around the parent WoodButtonBlock class allowing access to the constructor.
 */
public class TerraformButtonBlock extends WoodButtonBlock {
	public TerraformButtonBlock(Block.Settings settings) {
		super(settings);
	}
}
