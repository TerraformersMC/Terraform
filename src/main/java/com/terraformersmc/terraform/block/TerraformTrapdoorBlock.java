package com.terraformersmc.terraform.block;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.client.render.RenderLayer;

/**
 * A simple wrapper around the parent TrapdoorBlock class allowing access to the constructor.
 */
public class TerraformTrapdoorBlock extends TrapdoorBlock {
	public TerraformTrapdoorBlock(Settings settings) {
		super(settings);
		BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getCutout());
	}
}
