package com.terraformersmc.terraform.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class TerraformDesertPlantBlock extends PlantBlock {
	protected TerraformDesertPlantBlock(Settings block$Settings_1) {
		super(block$Settings_1);
	}

	@Override
	protected boolean canPlantOnTop(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
		Block block_1 = blockState_1.getBlock();
		if (block_1 == Blocks.SAND || block_1 == Blocks.RED_SAND) {
			return true;
		}
		return super.canPlantOnTop(blockState_1, blockView_1, blockPos_1);
	}
}
