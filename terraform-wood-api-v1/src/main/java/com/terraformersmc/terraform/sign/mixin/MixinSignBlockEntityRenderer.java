package com.terraformersmc.terraform.sign.mixin;

import com.terraformersmc.terraform.sign.TerraformSign;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.SignType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SignBlockEntityRenderer.class)
@Environment(EnvType.CLIENT)
public class MixinSignBlockEntityRenderer {

	@Inject(method = "render", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getSignTextureId(Lnet/minecraft/util/SignType;)Lnet/minecraft/client/util/SpriteIdentifier;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private void getModelTexture(SignBlockEntity sign, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, CallbackInfo ci, BlockState blockState, float g, SignType signType, SignBlockEntityRenderer.SignModel signModel, SpriteIdentifier spriteIdentifier) {
		if (blockState.getBlock() instanceof TerraformSign) {
			Identifier texture = ((TerraformSign) blockState.getBlock()).getTexture();
			spriteIdentifier = new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, texture);
		}
	}
}
