package com.terraformersmc.terraform.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.sound.BlockSoundGroup;

/**
 * A simple wrapper around the parent SaplingBlock class allowing access to the constructor.
 */
public class TerraformSaplingBlock extends SaplingBlock {

	public TerraformSaplingBlock(SaplingGenerator generator) {
		super(generator, FabricBlockSettings.of(Material.PLANT).collidable(false).ticksRandomly().hardness(0).sounds(BlockSoundGroup.GRASS).build());
	}

}
