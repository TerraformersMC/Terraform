package com.terraformersmc.terraform.mixin;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({AttachedStemBlock.class, CropBlock.class, StemBlock.class, PlantBlock.class})
public class MixinPlantingOnFarmland {
	@Inject(method = "canPlantOnTop", at = @At("HEAD"), cancellable = true)
	private void onCanPlantOnTop(BlockState floor, BlockView view, BlockPos pos, CallbackInfoReturnable<Boolean> callback) {
		if (floor.getBlock() instanceof FarmlandBlock) {
			callback.setReturnValue(true);
		}
	}
}
