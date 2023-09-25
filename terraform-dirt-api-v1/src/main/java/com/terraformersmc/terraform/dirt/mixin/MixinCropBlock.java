package com.terraformersmc.terraform.dirt.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.terraformersmc.terraform.dirt.TerraformDirtBlockTags;
import net.minecraft.block.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CropBlock.class)
public class MixinCropBlock {
	@WrapOperation(
			method = "getAvailableMoisture",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z")
	)
	@SuppressWarnings("unused")
	private static boolean terraformDirt$isOfFarmlandTag(BlockState instance, Block block, Operation<Boolean> original) {
		return original.call(instance, block) || (block == Blocks.FARMLAND && instance.isIn(TerraformDirtBlockTags.FARMLAND));
	}
}
