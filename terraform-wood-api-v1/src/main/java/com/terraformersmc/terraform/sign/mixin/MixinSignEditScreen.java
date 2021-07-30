package com.terraformersmc.terraform.sign.mixin;

import com.terraformersmc.terraform.sign.TerraformSign;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.SignType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SignEditScreen.class)
@Environment(EnvType.CLIENT)
public class MixinSignEditScreen {
	@Shadow
	@Final
	private SignBlockEntity sign;
	
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getSignTextureId(Lnet/minecraft/util/SignType;)Lnet/minecraft/client/util/SpriteIdentifier;"))
	private SpriteIdentifier getSignTextureId(SignType signType) {
		if (sign.getCachedState().getBlock() instanceof TerraformSign) {
			return new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ((TerraformSign) sign.getCachedState().getBlock()).getTexture());
		}
		return TexturedRenderLayers.getSignTextureId(signType);
	}
}
