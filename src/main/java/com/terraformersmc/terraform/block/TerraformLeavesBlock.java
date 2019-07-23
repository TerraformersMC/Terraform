package com.terraformersmc.terraform.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

/**
 * A simple wrapper around the parent LeavesBlock class allowing access to the constructor.
 */
public class TerraformLeavesBlock extends LeavesBlock {

	public TerraformLeavesBlock() {
		super(FabricBlockSettings.of(Material.LEAVES).hardness(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).build());
	}

}
