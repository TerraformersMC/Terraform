package com.terraformersmc.terraform.dirt.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.terraformersmc.terraform.dirt.TerraformDirtBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SugarCaneBlock.class)
public class MixinSugarCaneBlock {
	@WrapOperation(
			method = "canPlaceAt",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z", ordinal = 0)
	)
	@SuppressWarnings("unused")
	private boolean terraformDirt$canPlaceOnSoil(BlockState instance, TagKey<Block> dirtTag, Operation<Boolean> operation) {
		if (instance.isIn(TerraformDirtBlockTags.SOIL)) {
			return true;
		}

		return operation.call(instance, dirtTag);
	}
}
