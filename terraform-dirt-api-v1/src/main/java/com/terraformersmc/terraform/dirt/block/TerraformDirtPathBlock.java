package com.terraformersmc.terraform.dirt.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirtPathBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TerraformDirtPathBlock extends DirtPathBlock {
	private Block dirt;

	public TerraformDirtPathBlock(Block dirt, Settings settings) {
		super(settings);

		this.dirt = dirt;
	}

	public void onScheduledTick(BlockState state, World world, BlockPos pos, Random random) {
		world.setBlockState(pos, pushEntitiesUpBeforeBlockChange(state, dirt.getDefaultState(), world, pos));
	}
}
