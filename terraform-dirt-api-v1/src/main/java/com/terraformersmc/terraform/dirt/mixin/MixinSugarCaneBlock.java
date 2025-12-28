package com.terraformersmc.terraform.dirt.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.terraformersmc.terraform.dirt.api.TerraformDirtBlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SugarCaneBlock.class)
public class MixinSugarCaneBlock {
	@WrapOperation(
			method = "canSurvive",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 0)
	)
	@SuppressWarnings("unused")
	private boolean terraformDirt$canPlaceOnSoil(BlockState instance, TagKey<Block> dirtTag, Operation<Boolean> operation) {
		if (instance.is(TerraformDirtBlockTags.SOIL)) {
			return true;
		}

		return operation.call(instance, dirtTag);
	}
}
