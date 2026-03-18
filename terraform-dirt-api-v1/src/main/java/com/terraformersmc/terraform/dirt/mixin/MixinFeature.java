package com.terraformersmc.terraform.dirt.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.terraformersmc.terraform.dirt.api.TerraformDirtBlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Feature.class)
public class MixinFeature {
	@WrapMethod(
		method = "lambda$isReplaceable$0"
	)
	private static boolean terraformDirt$includeCustomSoil(TagKey<Block> cannotReplaceTag, BlockState state, Operation<Boolean> operation) {
		if (Blocks.DIRT.defaultBlockState().is(cannotReplaceTag) && state.is(TerraformDirtBlockTags.SOIL)) {
			return false;
		}

		return operation.call(cannotReplaceTag, state);
	}
}
