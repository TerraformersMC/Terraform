package com.terraformersmc.terraform.wood.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LeavesBlock.class)
@SuppressWarnings("unused")
public interface InvokerLeavesBlock {
	@Invoker
	static void callMakeDrippingWaterParticles(Level level, BlockPos pos, RandomSource random, BlockState belowState, BlockPos below) {
		throw new UnsupportedOperationException();
	}

	@Invoker
	default void callMakeFallingLeavesParticles(Level level, BlockPos pos, RandomSource random, BlockState belowState, BlockPos below) {
		throw new UnsupportedOperationException();
	}
}
