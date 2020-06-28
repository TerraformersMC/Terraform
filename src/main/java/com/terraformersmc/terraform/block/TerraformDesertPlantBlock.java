package com.terraformersmc.terraform.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class TerraformDesertPlantBlock extends PlantBlock {
	private final boolean onlySand;

	public TerraformDesertPlantBlock(Settings settings) {
		this(settings, false);
	}

	public TerraformDesertPlantBlock(Settings settings, boolean onlySand) {
		super(settings);
		this.onlySand = onlySand;
	}

	@Override
	public boolean canPlantOnTop(BlockState blockState, BlockView blockView, BlockPos pos) {
		if (onlySand) {
			return BlockTags.SAND.contains(blockState.getBlock());
		} else {
			return BlockTags.SAND.contains(blockState.getBlock()) || super.canPlantOnTop(blockState, blockView, pos);
		}
	}
}
