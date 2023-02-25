package com.terraformersmc.terraform.sign.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.HangingSignBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.terraformersmc.terraform.sign.TerraformSign;

@Mixin(HangingSignBlockEntityRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class MixinHangingSignBlockEntityRenderer {
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/HangingSignBlockEntityRenderer;renderSign(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IIFLnet/minecraft/block/WoodType;Lnet/minecraft/client/model/Model;)V"))
	private void setRenderedHangingSignBlockEntity(HangingSignBlockEntityRenderer renderer, MatrixStack matrices, VertexConsumerProvider verticesProvider, int light, int overlay, float scale, WoodType type, Model model, SignBlockEntity signBlockEntity) {
		MixinSignBlockEntityRenderer mixin = (MixinSignBlockEntityRenderer) (Object) renderer;

		mixin.terraform$renderedBlockEntity = signBlockEntity;
		mixin.renderSign(matrices, verticesProvider, light, overlay, scale, type, model);
		mixin.terraform$renderedBlockEntity = null;
	}

	@Inject(method = "getTextureId", at = @At("HEAD"), cancellable = true)
	private void getHangingSignTextureId(CallbackInfoReturnable<SpriteIdentifier> ci) {
		MixinSignBlockEntityRenderer mixin = (MixinSignBlockEntityRenderer) (Object) this;

		if (mixin.terraform$renderedBlockEntity != null) {
			Block block = mixin.terraform$renderedBlockEntity.getCachedState().getBlock();
			if (block instanceof TerraformSign) {
				ci.setReturnValue(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ((TerraformSign) block).getTexture()));
			}
		}
	}
}
