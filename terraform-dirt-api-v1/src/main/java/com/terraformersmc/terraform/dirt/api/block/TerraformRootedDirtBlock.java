package com.terraformersmc.terraform.dirt.api.block;

import net.minecraft.world.level.block.RootedDirtBlock;

/**
 * Custom rooted dirt block used to apply dirt block fixes to modded dirt path blocks.
 */
@SuppressWarnings("unused")
public class TerraformRootedDirtBlock extends RootedDirtBlock {
	public TerraformRootedDirtBlock(Properties properties) {
		super(properties);
	}
}
