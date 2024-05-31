package com.terraformersmc.terraform.sign.impl.mixin;

import com.terraformersmc.terraform.sign.api.TerraformHangingSign;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.screen.ingame.HangingSignEditScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HangingSignEditScreen.class)
@Environment(EnvType.CLIENT)
public class MixinHangingSignEditScreen {
	@Shadow
	@Final
	@Mutable
	private Identifier texture;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void terraformWood$initSignTextureId(SignBlockEntity signBlockEntity, boolean front, boolean filtered, CallbackInfo ci) {
		if (signBlockEntity.getCachedState().getBlock() instanceof TerraformHangingSign signBlock) {
			Identifier guiTexture = signBlock.getGuiTexture();
			this.texture = guiTexture.withSuffixedPath(".png");
		}
	}
}
