package com.terraformersmc.terraform.dirt.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.terraformersmc.terraform.dirt.TerraformDirtBlockTags;
import net.minecraft.block.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.entity.passive.RabbitEntity$EatCarrotCropGoal")
public class MixinRabbitEntity {
	@WrapOperation(
			method = "isTargetPos",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z")
	)
	@SuppressWarnings("unused")
	private boolean terraformDirt$isOnFarmland(BlockState instance, Block block, Operation<Boolean> operation) {
		if (Blocks.FARMLAND.equals(block) && instance.isIn(TerraformDirtBlockTags.FARMLAND)) {
			return true;
		}

		return operation.call(instance, block);
	}
}
