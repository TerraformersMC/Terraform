package com.terraformersmc.terraform.dirt.mixin;

import com.terraformersmc.terraform.dirt.TerraformDirtBlockTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

@Mixin({AttachedStemBlock.class, CropBlock.class, StemBlock.class})
public class MixinPlantingOnFarmland {
	@Inject(method = "canPlantOnTop", at = @At("HEAD"), cancellable = true)
	private void onCanPlantOnTop(BlockState floor, BlockView view, BlockPos pos, CallbackInfoReturnable<Boolean> callback) {
		if (floor.getBlock() instanceof FarmlandBlock && floor.isIn(TerraformDirtBlockTags.FARMLAND)) {
			callback.setReturnValue(true);
		}
	}
}
