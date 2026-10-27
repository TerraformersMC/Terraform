package com.terraformersmc.terraform.dirt.api.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PathBlock;

/**
 * Custom dirt path block used to apply dirt path fixes to modded dirt path blocks.
 */
@SuppressWarnings("unused")
public class TerraformPathBlock extends PathBlock {
	public TerraformPathBlock(Block baseBlock, Properties properties) {
		super(baseBlock, properties);
	}
}
