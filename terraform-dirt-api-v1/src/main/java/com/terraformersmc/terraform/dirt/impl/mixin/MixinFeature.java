package com.terraformersmc.terraform.dirt.impl.mixin;

import com.terraformersmc.terraform.dirt.api.TerraformDirtBlockTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Feature.class)
public class MixinFeature {
	@Inject(method = "isDirt(Lnet/minecraft/world/level/block/state/BlockState;)Z", at = @At("HEAD"), cancellable = true)
	private static void terraformDirt$includeCustomSoil(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (state.is(TerraformDirtBlockTags.SOIL)) {
			cir.setReturnValue(true);
		}
	}
}
