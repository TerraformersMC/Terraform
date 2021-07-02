package com.terraformersmc.terraform.dirt.mixin;

import com.terraformersmc.terraform.dirt.TerraformDirtBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Feature.class)
public class MixinFeature {
	@Inject(method = "isSoil(Lnet/minecraft/block/BlockState;)Z", at = @At("HEAD"), cancellable = true)
	private static void terraform$includeCustomSoil(BlockState state, CallbackInfoReturnable<Boolean> callback) {
		if (TerraformDirtBlockTags.SOIL.contains(state.getBlock())) {
			callback.setReturnValue(true);
		}
	}
}
