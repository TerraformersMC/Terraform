package com.terraformersmc.terraform.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import com.terraformersmc.terraform.tag.TerraformBlockTags;

@Mixin({AttachedStemBlock.class, CropBlock.class, StemBlock.class, PlantBlock.class})
public class MixinPlantingOnFarmland {
	@Inject(method = "canPlantOnTop", at = @At("HEAD"), cancellable = true)
	private void onCanPlantOnTop(BlockState floor, BlockView view, BlockPos pos, CallbackInfoReturnable<Boolean> callback) {
		Block block = floor.getBlock();
		if (block instanceof FarmlandBlock && block.matches(TerraformBlockTags.FARMLAND)) {
			callback.setReturnValue(true);
		}
	}
}
