package com.terraformersmc.terraform.block;

import net.minecraft.block.Block;
import net.minecraft.block.StoneButtonBlock;

/**
 * A simple wrapper around the parent StoneButtonBlock class allowing access to the constructor.
 */
public class TerraformStoneButtonBlock extends StoneButtonBlock {
	public TerraformStoneButtonBlock(Block.Settings settings) {
		super(settings);
	}
}
