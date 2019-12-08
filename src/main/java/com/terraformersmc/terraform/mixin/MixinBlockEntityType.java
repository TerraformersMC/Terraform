package com.terraformersmc.terraform.mixin;

import com.terraformersmc.terraform.util.TerraformSign;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityType.class)
public class MixinBlockEntityType {
    @Inject(method = "supports", at = @At("HEAD"), cancellable = true)
    private void supports(Block block, CallbackInfoReturnable info) {
        //noinspection EqualsBetweenInconvertibleTypes
        if (BlockEntityType.SIGN.equals(this) && block instanceof TerraformSign) {
            //noinspection unchecked
            info.setReturnValue(true);
        }
    }
}
