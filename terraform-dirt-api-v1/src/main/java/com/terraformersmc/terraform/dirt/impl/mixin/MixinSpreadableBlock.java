package com.terraformersmc.terraform.dirt.impl.mixin;

import com.terraformersmc.terraform.dirt.api.block.TerraformGrassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SpreadableBlock.class)
public abstract class MixinSpreadableBlock {
	@Inject(method = "randomTick",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void terraformDirt$onScheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo info, BlockState defaultState, int i, BlockPos spreadingPos) {
		Block grassBlock = TerraformGrassBlock.GRASS_SPREADS_TO.get(world.getBlockState(spreadingPos).getBlock());
		if (grassBlock != null) {
			BlockState grassDefaultState = grassBlock.getDefaultState();
			if (TerraformGrassBlock.canSpread(grassDefaultState, world, spreadingPos)) {
				world.setBlockState(spreadingPos, grassDefaultState.with(SpreadableBlock.SNOWY, world.getBlockState(spreadingPos.up()).getBlock() == Blocks.SNOW));
			}
		}
	}
}
