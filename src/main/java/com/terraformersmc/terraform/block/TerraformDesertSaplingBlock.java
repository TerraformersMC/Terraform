package com.terraformersmc.terraform.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class TerraformDesertSaplingBlock extends TerraformSaplingBlock {
	public TerraformDesertSaplingBlock(SaplingGenerator generator) {
		super(generator);
	}

	@Override
	protected boolean canPlantOnTop(BlockState blockState, BlockView blockView, BlockPos pos) {
		if (BlockTags.SAND.contains(blockState.getBlock())) {
			return true;
		}
		return super.canPlantOnTop(blockState, blockView, pos);
	}
}
