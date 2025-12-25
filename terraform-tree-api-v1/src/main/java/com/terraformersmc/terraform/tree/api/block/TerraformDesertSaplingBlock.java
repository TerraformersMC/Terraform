package com.terraformersmc.terraform.tree.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;

public class TerraformDesertSaplingBlock extends SaplingBlock {
	private final boolean onlySand;

	public TerraformDesertSaplingBlock(TreeGrower generator, Properties settings) {
		this(generator, false, settings);
	}

	public TerraformDesertSaplingBlock(TreeGrower generator, boolean onlySand, Properties settings) {
		super(generator, settings);
		this.onlySand = onlySand;
	}

	@Override
	public boolean mayPlaceOn(BlockState blockState, BlockGetter blockView, BlockPos pos) {
		if (onlySand) {
			return blockState.is(BlockTags.SAND);
		} else {
			return blockState.is(BlockTags.SAND) || super.mayPlaceOn(blockState, blockView, pos);
		}
	}
}
