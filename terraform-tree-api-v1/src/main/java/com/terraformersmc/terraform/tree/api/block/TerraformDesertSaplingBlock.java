package com.terraformersmc.terraform.tree.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("unused")
public class TerraformDesertSaplingBlock extends SaplingBlock {
	private final boolean onlySand;

	public TerraformDesertSaplingBlock(TreeGrower treeGrower, Properties properties) {
		this(treeGrower, false, properties);
	}

	public TerraformDesertSaplingBlock(TreeGrower treeGrower, boolean onlySand, Properties properties) {
		super(treeGrower, properties);

		this.onlySand = onlySand;
	}

	@Override
	public boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		if (onlySand) {
			return state.is(BlockTags.SAND);
		} else {
			return state.is(BlockTags.SAND) || super.mayPlaceOn(state, level, pos);
		}
	}
}
