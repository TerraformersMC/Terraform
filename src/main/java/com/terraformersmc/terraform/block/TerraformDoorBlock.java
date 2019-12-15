package com.terraformersmc.terraform.block;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.client.render.RenderLayer;

/**
 * A simple wrapper around the parent DoorBlock class allowing access to the constructor.
 */
public class TerraformDoorBlock extends DoorBlock {
	public TerraformDoorBlock(Block.Settings settings) {
		super(settings);
		BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getCutout());
	}
}
