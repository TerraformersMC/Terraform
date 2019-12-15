package com.terraformersmc.terraform.block;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class TerraformDesertPlantBlock extends PlantBlock {
	public TerraformDesertPlantBlock(Settings settings) {
		super(settings);
		BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getCutout());
	}

	@Override
	public boolean canPlantOnTop(BlockState blockState, BlockView blockView, BlockPos pos) {
		if (BlockTags.SAND.contains(blockState.getBlock())) {
			return true;
		}
		return super.canPlantOnTop(blockState, blockView, pos);
	}
}
