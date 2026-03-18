package com.terraformersmc.terraform.dirt.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.terraformersmc.terraform.dirt.api.TerraformDirtBlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StemBlock.class)
public class MixinStemBlock {
	@WrapOperation(
			method = "randomTick",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/TagKey;)Z")
	)
	@SuppressWarnings("unused")
	private boolean terraformDirt$isOnFarmland(BlockState instance, TagKey<Block> tag, Operation<Boolean> operation) {
		if (instance.is(TerraformDirtBlockTags.FARMLAND)) {
			return true;
		}

		return operation.call(instance, tag);
	}
}
