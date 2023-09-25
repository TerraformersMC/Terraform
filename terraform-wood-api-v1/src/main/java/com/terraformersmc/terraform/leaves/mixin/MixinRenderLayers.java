package com.terraformersmc.terraform.leaves.mixin;

import com.terraformersmc.terraform.wood.block.SmallLogBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;

import com.terraformersmc.terraform.leaves.block.ExtendedLeavesBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Mixin(RenderLayers.class)
@Environment(EnvType.CLIENT)
public class MixinRenderLayers {
	@Shadow
	private static boolean fancyGraphicsOrBetter;

	@Inject(method = "getBlockLayer", at = @At("HEAD"), cancellable = true)
	private static void terraformWood$onGetBlockRenderLayer(BlockState state, CallbackInfoReturnable<RenderLayer> cir) {
		Block block = state.getBlock();
		if (block instanceof ExtendedLeavesBlock || block instanceof SmallLogBlock && state.get(SmallLogBlock.HAS_LEAVES)) {
			cir.setReturnValue(fancyGraphicsOrBetter ? RenderLayer.getCutoutMipped() : RenderLayer.getSolid());
		}
	}
}
