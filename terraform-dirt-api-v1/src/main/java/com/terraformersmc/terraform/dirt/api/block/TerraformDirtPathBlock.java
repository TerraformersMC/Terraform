package com.terraformersmc.terraform.dirt.api.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirtPathBlock;

public class TerraformDirtPathBlock extends DirtPathBlock {
	/**
	 * @deprecated the "dirt" block is no longer used by TerraformDirtPathBlock, use the other constructor.
	 */
	@Deprecated
	public TerraformDirtPathBlock(Block dirt, Properties settings) {
		super(settings);
	}

	public TerraformDirtPathBlock(Properties settings) {
		super(settings);
	}
}
