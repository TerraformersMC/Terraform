package com.terraformersmc.terraform.dirt.api.block;

import net.minecraft.world.level.block.DirtPathBlock;

/**
 * Custom dirt path block used to apply dirt path fixes to modded dirt path blocks.
 */
@SuppressWarnings("unused")
public class TerraformDirtPathBlock extends DirtPathBlock {
	public TerraformDirtPathBlock(Properties properties) {
		super(properties);
	}
}
