package com.terraformersmc.terraform.dirt.impl.mixin;

import com.terraformersmc.terraform.dirt.api.block.TerraformGrassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SpreadingSnowyDirtBlock.class)
public abstract class MixinSpreadableBlock {
	@Inject(method = "randomTick",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void terraformDirt$onScheduledTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo info, BlockState defaultState, int i, BlockPos spreadingPos) {
		Block grassBlock = TerraformGrassBlock.GRASS_SPREADS_TO.get(world.getBlockState(spreadingPos).getBlock());
		if (grassBlock != null) {
			BlockState grassDefaultState = grassBlock.defaultBlockState();
			if (TerraformGrassBlock.canPropagate(grassDefaultState, world, spreadingPos)) {
				world.setBlockAndUpdate(spreadingPos, grassDefaultState.setValue(SpreadingSnowyDirtBlock.SNOWY, world.getBlockState(spreadingPos.above()).getBlock() == Blocks.SNOW));
			}
		}
	}
}
