package com.terraformersmc.terraform.dirt.mixin;

import com.terraformersmc.terraform.dirt.DirtBlocks;
import com.terraformersmc.terraform.dirt.TerraformDirtBlockTags;
import com.terraformersmc.terraform.dirt.TerraformDirtRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.trunk.TrunkPlacer;

@Mixin(TrunkPlacer.class)
public class MixinTrunkPlacer {
	@Inject(method = "setToDirt", at = @At("HEAD"), cancellable = true)
	private static void notAlwaysDirt(ModifiableTestableWorld world, BlockPos pos, CallbackInfo ci) {
		if (!world.testBlockState(pos, state -> state.isIn(TerraformDirtBlockTags.SOIL))) {
			return;
		}

		Block dirt = TerraformDirtRegistry.getFromWorld(world, pos).map(DirtBlocks::getDirt).orElse(Blocks.DIRT);

		TreeFeature.setBlockStateWithoutUpdatingNeighbors(world, pos, dirt.getDefaultState());
		ci.cancel();
	}
}
