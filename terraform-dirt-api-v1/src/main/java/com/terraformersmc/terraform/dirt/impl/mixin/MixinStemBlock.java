package com.terraformersmc.terraform.dirt.impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.terraformersmc.terraform.dirt.api.TerraformDirtBlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StemBlock.class)
public class MixinStemBlock {
	@WrapOperation(
			method = "randomTick",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z")
	)
	@SuppressWarnings("unused")
	private boolean terraformDirt$isOnFarmland(BlockState instance, Object block, Operation<Boolean> operation) {
		if (Blocks.FARMLAND.equals(block) && instance.is(TerraformDirtBlockTags.FARMLAND)) {
			return true;
		}

		return operation.call(instance, block);
	}
}
