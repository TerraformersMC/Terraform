package com.terraformersmc.terraform.sign.mixin;

import com.terraformersmc.terraform.sign.TerraformSign;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SignEditScreen.class)
@Environment(EnvType.CLIENT)
public class MixinSignEditScreen {
	@ModifyVariable(method = "renderSignBackground", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getSignTextureId(Lnet/minecraft/block/WoodType;)Lnet/minecraft/client/util/SpriteIdentifier;"))
	private SpriteIdentifier getSignTextureId(SpriteIdentifier spriteIdentifier, MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, BlockState state) {
		if (state.getBlock() instanceof TerraformSign) {
			return new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ((TerraformSign) state.getBlock()).getTexture());
		}
		return spriteIdentifier;
	}
}
