package com.terraformersmc.terraform.dirt.impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.terraformersmc.terraform.dirt.api.TerraformDirtBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StemBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StemBlock.class)
public class MixinStemBlock {
	@WrapOperation(
			method = "randomTick",
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
