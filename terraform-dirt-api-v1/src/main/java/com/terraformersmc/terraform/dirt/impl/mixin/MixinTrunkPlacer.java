package com.terraformersmc.terraform.dirt.impl.mixin;

import com.terraformersmc.terraform.dirt.api.DirtBlocks;
import com.terraformersmc.terraform.dirt.api.TerraformDirtBlockTags;
import com.terraformersmc.terraform.dirt.impl.registry.TerraformDirtRegistryImpl;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(TrunkPlacer.class)
public class MixinTrunkPlacer {
	@Inject(method = "setToDirt", at = @At("HEAD"), cancellable = true)
	private static void terraformDirt$notAlwaysDirt(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos pos, TreeFeatureConfig config, CallbackInfo ci) {
		if (world.testBlockState(pos, state -> state.isIn(TerraformDirtBlockTags.SOIL))) {
			Block dirt = TerraformDirtRegistryImpl.getFromWorld(world, pos).map(DirtBlocks::getDirt).orElse(Blocks.DIRT);

			replacer.accept(pos, dirt.getDefaultState());

			ci.cancel();
		}
	}
}
