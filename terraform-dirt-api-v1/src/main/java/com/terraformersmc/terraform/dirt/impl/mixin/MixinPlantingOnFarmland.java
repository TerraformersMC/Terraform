package com.terraformersmc.terraform.dirt.impl.mixin;

import com.terraformersmc.terraform.dirt.api.TerraformDirtBlockTags;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({AttachedStemBlock.class, CropBlock.class, PitcherCropBlock.class, StemBlock.class})
public class MixinPlantingOnFarmland {
	@Inject(method = "canPlantOnTop", at = @At("HEAD"), cancellable = true)
	private void terraformDirt$isOnFarmland(BlockState floor, BlockView view, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (floor.getBlock() instanceof FarmlandBlock && floor.isIn(TerraformDirtBlockTags.FARMLAND)) {
			cir.setReturnValue(true);
		}
	}
}
