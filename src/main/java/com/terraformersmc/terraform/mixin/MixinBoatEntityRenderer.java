package com.terraformersmc.terraform.mixin;

import com.terraformersmc.terraform.entity.TerraformBoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;

@Mixin(BoatEntityRenderer.class)
public class MixinBoatEntityRenderer {
    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    private void getModelTexture(BoatEntity boat, CallbackInfoReturnable<Identifier> info) {
		if(boat instanceof TerraformBoatEntity) {
			info.setReturnValue(((TerraformBoatEntity) boat).getBoatSkin());
		}
    }
}
