package com.terraformersmc.terraform.leaves.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;

/**
 * A leaf block that does not block light.
 */
public class TransparentLeavesBlock extends LeavesBlock {
	public TransparentLeavesBlock(Block.Settings settings) {
		super(settings);
	}

	@Override
	public int getOpacity(BlockState state) {
		return 0;
	}
}
