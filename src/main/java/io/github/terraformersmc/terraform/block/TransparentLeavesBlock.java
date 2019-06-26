package io.github.terraformersmc.terraform.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

/**
 * A leaf block that does not block light.
 */
public class TransparentLeavesBlock extends LeavesBlock {
	public TransparentLeavesBlock(Block.Settings settings) {
		super(settings);
	}

	@Override
	public int getLightSubtracted(BlockState state, BlockView view, BlockPos pos) {
		return 0;
	}
}
