package com.terraformersmc.terraform.dirt.mixin;

import com.terraformersmc.terraform.dirt.api.TerraformDirtBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({AttachedStemBlock.class, CropBlock.class, PitcherCropBlock.class, StemBlock.class})
public class MixinPlantingOnFarmland {
	@Inject(method = "mayPlaceOn", at = @At("HEAD"), cancellable = true)
	private void terraformDirt$isOnFarmland(BlockState floor, BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (floor.getBlock() instanceof FarmlandBlock && floor.is(TerraformDirtBlockTags.FARMLAND)) {
			cir.setReturnValue(true);
		}
	}
}
