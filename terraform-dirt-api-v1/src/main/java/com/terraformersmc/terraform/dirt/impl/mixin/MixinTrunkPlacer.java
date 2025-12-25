package com.terraformersmc.terraform.dirt.impl.mixin;

import com.terraformersmc.terraform.dirt.api.DirtBlocks;
import com.terraformersmc.terraform.dirt.api.TerraformDirtBlockTags;
import com.terraformersmc.terraform.dirt.impl.registry.TerraformDirtRegistryImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;

@Mixin(TrunkPlacer.class)
public class MixinTrunkPlacer {
	@Inject(method = "setDirtAt", at = @At("HEAD"), cancellable = true)
	private static void terraformDirt$notAlwaysDirt(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos pos, TreeConfiguration config, CallbackInfo ci) {
		if (world.isStateAtPosition(pos, state -> state.is(TerraformDirtBlockTags.SOIL))) {
			Block dirt = TerraformDirtRegistryImpl.getFromWorld(world, pos).map(DirtBlocks::getDirt).orElse(Blocks.DIRT);

			replacer.accept(pos, dirt.defaultBlockState());

			ci.cancel();
		}
	}
}
