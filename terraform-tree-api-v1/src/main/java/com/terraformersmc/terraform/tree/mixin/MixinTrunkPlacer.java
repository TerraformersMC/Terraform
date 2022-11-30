package com.terraformersmc.terraform.tree.mixin;

import java.util.function.BiConsumer;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TrunkPlacer.class)
public class MixinTrunkPlacer {
	@Inject(method = "setToDirt", at = @At("HEAD"), cancellable = true)
	private static void notAlwaysDirt(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos pos, TreeFeatureConfig config, CallbackInfo ci) {
		if (world.testBlockState(pos, state -> state.isIn(BlockTags.SAND))) {
			ci.cancel();
		}
	}
}
