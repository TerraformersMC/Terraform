package com.terraformersmc.terraform.dirt.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirtPathBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class TerraformDirtPathBlock extends DirtPathBlock {
	private Block dirt;

	public TerraformDirtPathBlock(Block dirt, Settings settings) {
		super(settings);

		this.dirt = dirt;
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		world.setBlockState(pos, pushEntitiesUpBeforeBlockChange(state, dirt.getDefaultState(), world, pos));
	}
}
