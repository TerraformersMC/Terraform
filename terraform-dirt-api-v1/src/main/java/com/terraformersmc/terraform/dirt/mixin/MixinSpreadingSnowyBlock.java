package com.terraformersmc.terraform.dirt.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.terraformersmc.terraform.dirt.api.block.TerraformGrassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpreadingSnowyBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpreadingSnowyBlock.class)
public abstract class MixinSpreadingSnowyBlock {
	@Inject(method = "randomTick",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z")
	)
	private void terraformDirt$customGrassSpreads(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo info, @Local(ordinal = 1) BlockPos spreadingPos) {
		Block grassBlock = TerraformGrassBlock.GRASS_SPREADS_TO.get(level.getBlockState(spreadingPos).getBlock());
		if (grassBlock != null) {
			BlockState grassDefaultState = grassBlock.defaultBlockState();
			if (TerraformGrassBlock.canPropagate(grassDefaultState, level, spreadingPos)) {
				level.setBlockAndUpdate(spreadingPos, grassDefaultState.setValue(SpreadingSnowyBlock.SNOWY, level.getBlockState(spreadingPos.above()).getBlock() == Blocks.SNOW));
			}
		}
	}
}
