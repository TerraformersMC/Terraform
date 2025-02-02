package com.terraformersmc.terraform.leaves.impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.terraformersmc.terraform.leaves.api.block.ExtendedLeavesBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.state.property.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LeavesBlock.class)
public class MixinLeavesBlock {
	@WrapOperation(
			method = "<init>",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"
			)
	)
	@SuppressWarnings("unused")
	private Object terraform$skipWithDistanceProperty(BlockState instance, Property<?> property, Comparable<?> value, Operation<Object> operation) {
		//noinspection ConstantConditions
		if (((Object) this) instanceof ExtendedLeavesBlock extendedLeavesBlock) {
			return instance;
		}

		return operation.call(instance, property, value);
	}

	@WrapOperation(
			method = "<init>",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/block/LeavesBlock;setDefaultState(Lnet/minecraft/block/BlockState;)V"
			)
	)
	@SuppressWarnings("unused")
	private void terraform$skipSetDefaultState(LeavesBlock instance, BlockState state, Operation<Void> operation) {
		if (instance instanceof ExtendedLeavesBlock) {
			// Prevent LeavesBlock from attempting to set its incorrect DISTANCE property.
			return;
		}

		operation.call(instance, state);
	}
}
