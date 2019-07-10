package io.github.terraformersmc.terraform.mixin;

import io.github.terraformersmc.terraform.util.TerraformSign;
import net.minecraft.block.Block;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignBlockEntityRenderer.class)
public class MixinSignBlockEntityRenderer {
    @Inject(method = "getModelTexture", at = @At("HEAD"), cancellable = true)
    private void getModelTexture(Block block, CallbackInfoReturnable info) {
        if (block instanceof TerraformSign) {
            //noinspection unchecked
            info.setReturnValue(((TerraformSign) block).getTexture());
        }
    }
}
