package com.terraformersmc.terraform.leaves.block;

import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;

/**
 * A simple wrapper around the parent LeavesBlock class allowing access to the constructor.
 */
public class TerraformLeavesBlock extends LeavesBlock {

	public TerraformLeavesBlock() {
		super(Settings.copy(Blocks.OAK_LEAVES));
	}

}
