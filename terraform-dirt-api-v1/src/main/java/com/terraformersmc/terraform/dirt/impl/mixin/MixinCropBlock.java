package com.terraformersmc.terraform.dirt.impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.terraformersmc.terraform.dirt.api.TerraformDirtBlockTags;
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
	private static boolean terraformDirt$isOnFarmland(BlockState instance, Block block, Operation<Boolean> original) {
		if (Blocks.FARMLAND.equals(block) && instance.isIn(TerraformDirtBlockTags.FARMLAND)) {
			return true;
		}

		return original.call(instance, block);
	}
}
