package com.terraformersmc.terraform.leaves.impl.mixin;

import com.terraformersmc.terraform.leaves.api.block.ExtendedLeavesBlock;
import com.terraformersmc.terraform.wood.api.block.SmallLogBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.BlockRenderLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockRenderLayers.class)
@Environment(EnvType.CLIENT)
public class MixinBlockRenderLayers {
	@Inject(method = "getBlockLayer", at = @At("HEAD"), cancellable = true)
	private static void terraformWood$onGetBlockRenderLayer(BlockState state, CallbackInfoReturnable<BlockRenderLayer> cir) {
		Block block = state.getBlock();
		if (block instanceof ExtendedLeavesBlock || block instanceof SmallLogBlock && state.get(SmallLogBlock.HAS_LEAVES)) {
			cir.setReturnValue(BlockRenderLayer.CUTOUT);
		}
	}
}
