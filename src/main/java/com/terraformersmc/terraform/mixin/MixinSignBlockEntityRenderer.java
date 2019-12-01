package com.terraformersmc.terraform.mixin;

import com.terraformersmc.terraform.util.TerraformSign;
import net.minecraft.block.Block;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignBlockEntityRenderer.class)
public class MixinSignBlockEntityRenderer {
    @Inject(method = "getModelTexture", at = @At("HEAD"), cancellable = true)
    private void getModelTexture(Block block, CallbackInfoReturnable<SpriteIdentifier> info) {
        if (block instanceof TerraformSign) {
			Identifier texture = ((TerraformSign) block).getTexture();

			if(texture.getPath().startsWith("textures/")) {
				throw new IllegalArgumentException("getTexture() should no longer prefix the path with 'textures/'");
			}

        	info.setReturnValue(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, texture));
        }
    }
}
