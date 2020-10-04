package com.terraformersmc.terraform.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.sound.BlockSoundGroup;

/**
 * A simple wrapper around the parent SaplingBlock class allowing access to the constructor.
 */
public class TerraformSaplingBlock extends SaplingBlock {

	public TerraformSaplingBlock(SaplingGenerator generator, Settings settings) {
		super(generator, settings);
	}

	public TerraformSaplingBlock(SaplingGenerator generator) {
		this(generator, FabricBlockSettings.of(Material.PLANT).collidable(false).ticksRandomly().hardness(0).sounds(BlockSoundGroup.GRASS));
	}

}
