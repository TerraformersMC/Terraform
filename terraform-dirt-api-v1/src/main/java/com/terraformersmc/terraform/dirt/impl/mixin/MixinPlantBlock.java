package com.terraformersmc.terraform.dirt.impl.mixin;

import com.terraformersmc.terraform.dirt.api.TerraformDirtBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlantBlock.class)
public class MixinPlantBlock {
	@Inject(method = "canPlantOnTop", at = @At("HEAD"), cancellable = true)
	private void terraformDirt$hookPlantOnTop(BlockState state, BlockView view, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (state.isIn(TerraformDirtBlockTags.SOIL) || state.isIn(TerraformDirtBlockTags.FARMLAND)) {
			cir.setReturnValue(true);
		}
	}
}
