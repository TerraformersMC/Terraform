package com.terraformersmc.terraform.block;

import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;

import net.fabricmc.fabric.api.block.FabricBlockSettings;

/**
 * A simple wrapper around the parent LeavesBlock class allowing access to the constructor.
 */
public class TerraformLeavesBlock extends LeavesBlock {

	public TerraformLeavesBlock() {
		super(FabricBlockSettings.copy(Blocks.OAK_LEAVES).build());
	}

}
