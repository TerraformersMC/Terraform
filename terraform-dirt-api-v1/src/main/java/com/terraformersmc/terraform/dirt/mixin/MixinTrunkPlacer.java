package com.terraformersmc.terraform.dirt.mixin;

import com.terraformersmc.terraform.dirt.api.DirtBlocks;
import com.terraformersmc.terraform.dirt.api.TerraformDirtBlockTags;
import com.terraformersmc.terraform.dirt.impl.registry.TerraformDirtRegistryImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(TrunkPlacer.class)
public class MixinTrunkPlacer {
	@Inject(method = "placeBelowTrunkBlock", at = @At("HEAD"), cancellable = true)
	private static void terraformDirt$notAlwaysDirt(WorldGenLevel level, BiConsumer<BlockPos, BlockState> trunkSetter, RandomSource random, BlockPos pos, TreeConfiguration config, CallbackInfo ci) {
		if (level.isStateAtPosition(pos, state -> state.is(TerraformDirtBlockTags.SOIL))) {
			Block dirt = TerraformDirtRegistryImpl.getFromLevel(level, pos)
				.map(DirtBlocks::dirtBlock).orElse(Blocks.DIRT);

			trunkSetter.accept(pos, dirt.defaultBlockState());

			ci.cancel();
		}
	}
}
