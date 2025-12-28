package com.terraformersmc.terraform.dirt.mixin;

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
public abstract class MixinSpreadingSnowyDirtBlock {
	@Inject(method = "randomTick",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z"),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void terraformDirt$customGrassSpreads(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo info, BlockState defaultState, int i, BlockPos spreadingPos) {
		Block grassBlock = TerraformGrassBlock.GRASS_SPREADS_TO.get(level.getBlockState(spreadingPos).getBlock());
		if (grassBlock != null) {
			BlockState grassDefaultState = grassBlock.defaultBlockState();
			if (TerraformGrassBlock.canPropagate(grassDefaultState, level, spreadingPos)) {
				level.setBlockAndUpdate(spreadingPos, grassDefaultState.setValue(SpreadingSnowyDirtBlock.SNOWY, level.getBlockState(spreadingPos.above()).getBlock() == Blocks.SNOW));
			}
		}
	}
}
