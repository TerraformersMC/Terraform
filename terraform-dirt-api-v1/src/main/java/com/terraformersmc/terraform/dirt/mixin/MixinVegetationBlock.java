package com.terraformersmc.terraform.dirt.mixin;

import com.terraformersmc.terraform.dirt.api.TerraformDirtBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VegetationBlock.class)
public class MixinVegetationBlock {
	@Inject(method = "mayPlaceOn", at = @At("HEAD"), cancellable = true)
	private void terraformDirt$hookPlantOnTop(BlockState state, BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (state.is(TerraformDirtBlockTags.SOIL) || state.is(TerraformDirtBlockTags.FARMLAND)) {
			cir.setReturnValue(true);
		}
	}
}
