package com.terraformersmc.terraform.dirt.api.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * A custom farm block for new farmland.
 */
@SuppressWarnings("unused")
public class TerraformFarmlandBlock extends FarmlandBlock {
	public TerraformFarmlandBlock(Block baseBlock, BlockBehaviour.Properties properties) {
		super(baseBlock, properties);
	}
}
