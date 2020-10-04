package com.terraformersmc.terraform.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;

/**
 * A simple wrapper around the parent LeavesBlock class allowing access to the constructor.
 */
public class TerraformLeavesBlock extends LeavesBlock {

	public TerraformLeavesBlock() {
		super(FabricBlockSettings.copy(Blocks.OAK_LEAVES));
	}

}
