package com.terraformersmc.terraform.wood.block;

import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;

/**
 * A simple wrapper around the parent DoorBlock class allowing access to the constructor.
 */
public class TerraformDoorBlock extends DoorBlock {
	public TerraformDoorBlock(Block.Settings settings) {
		super(settings);
	}
}
