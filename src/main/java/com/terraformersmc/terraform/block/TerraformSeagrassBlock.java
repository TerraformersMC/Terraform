package com.terraformersmc.terraform.block;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

/**
 * A custom seagrass block that allows the specification of a custom tall variant.
 */
public class TerraformSeagrassBlock extends SeagrassBlock {
	private Block tall;

	public TerraformSeagrassBlock(Block tall, Block.Settings settings) {
		super(settings);

		this.tall = tall;
		BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getCutout());
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		BlockState tallBottom = tall.getDefaultState();
		BlockState tallTop = tallBottom.with(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER);
		BlockPos upper = pos.up();

		if (world.getBlockState(upper).getBlock() == Blocks.AIR) {
			world.setBlockState(pos, tallBottom, 2);
			world.setBlockState(upper, tallTop, 2);
		}
	}
}
