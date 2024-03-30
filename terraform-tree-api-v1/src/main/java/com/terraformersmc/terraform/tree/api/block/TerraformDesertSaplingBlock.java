package com.terraformersmc.terraform.tree.api.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class TerraformDesertSaplingBlock extends SaplingBlock {
	private final boolean onlySand;

	public TerraformDesertSaplingBlock(SaplingGenerator generator, Settings settings) {
		this(generator, settings, false);
	}

	public TerraformDesertSaplingBlock(SaplingGenerator generator, Settings settings, boolean onlySand) {
		super(generator, settings);
		this.onlySand = onlySand;
	}

	@Override
	public boolean canPlantOnTop(BlockState blockState, BlockView blockView, BlockPos pos) {
		if (onlySand) {
			return blockState.isIn(BlockTags.SAND);
		} else {
			return blockState.isIn(BlockTags.SAND) || super.canPlantOnTop(blockState, blockView, pos);
		}
	}
}
