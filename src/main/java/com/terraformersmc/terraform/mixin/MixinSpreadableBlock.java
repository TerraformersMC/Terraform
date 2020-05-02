package com.terraformersmc.terraform.mixin;

import com.terraformersmc.terraform.block.TerraformGrassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(SpreadableBlock.class)
public abstract class MixinSpreadableBlock {
	@Shadow
	private static boolean canSpread(BlockState state, WorldView worldView, BlockPos pos) {
		return false;
	}

	@Inject(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;method_27852(Lnet/minecraft/block/Block;)Z"), locals = LocalCapture.CAPTURE_FAILHARD)
	private void onScheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo info, BlockState defaultState, int i, BlockPos spreadingPos) {
		Block grassBlock = TerraformGrassBlock.GRASS_SPREADS_TO.get(world.getBlockState(spreadingPos).getBlock());
		if (grassBlock != null) {
			BlockState grassDefaultState = grassBlock.getDefaultState();
			if (TerraformGrassBlock.canSpread(grassDefaultState, world, spreadingPos)) {
				world.setBlockState(spreadingPos, grassDefaultState.with(SpreadableBlock.SNOWY, world.getBlockState(spreadingPos.up()).getBlock() == Blocks.SNOW));
			}
		}
	}
}
