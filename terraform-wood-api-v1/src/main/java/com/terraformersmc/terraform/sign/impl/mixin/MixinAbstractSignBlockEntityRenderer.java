package com.terraformersmc.terraform.sign.impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.AbstractSignBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(AbstractSignBlockEntityRenderer.class)
public abstract class MixinAbstractSignBlockEntityRenderer {
	@Unique
	protected SignBlockEntity terraform$renderedBlockEntity;

	@WrapOperation(
			method = "render(Lnet/minecraft/block/entity/SignBlockEntity;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/block/BlockState;Lnet/minecraft/block/AbstractSignBlock;Lnet/minecraft/block/WoodType;Lnet/minecraft/client/model/Model;)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/AbstractSignBlockEntityRenderer;renderSign(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/block/WoodType;Lnet/minecraft/client/model/Model;)V")
	)
	@SuppressWarnings("unused")
	private void terraformWood$setRenderedBlockEntity(AbstractSignBlockEntityRenderer instance, MatrixStack matrices, VertexConsumerProvider verticesProvider, int light, int overlay, WoodType type, Model model, Operation<Void> original, @Local SignBlockEntity blockEntity) {
		this.terraform$renderedBlockEntity = blockEntity;
		original.call(instance, matrices, verticesProvider, light, overlay, type, model);
		this.terraform$renderedBlockEntity = null;
	}
}
