package com.terraformersmc.terraform.dirt.block;

import net.minecraft.block.Block;
import net.minecraft.block.DirtPathBlock;

public class TerraformDirtPathBlock extends DirtPathBlock {
	/**
	 * @deprecated the "dirt" block is no longer used by TerraformDirtPathBlock, use the other constructor.
	 */
	@Deprecated
	public TerraformDirtPathBlock(Block dirt, Settings settings) {
		super(settings);
	}

	public TerraformDirtPathBlock(Settings settings) {
		super(settings);
	}
}
