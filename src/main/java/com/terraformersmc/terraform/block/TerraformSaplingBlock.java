package com.terraformersmc.terraform.block;

import net.minecraft.block.Material;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.sound.BlockSoundGroup;

import net.fabricmc.fabric.api.block.FabricBlockSettings;

/**
 * A simple wrapper around the parent SaplingBlock class allowing access to the constructor.
 */
public class TerraformSaplingBlock extends SaplingBlock {

	public TerraformSaplingBlock(SaplingGenerator generator, Settings settings) {
		super(generator, settings);
	}

}
