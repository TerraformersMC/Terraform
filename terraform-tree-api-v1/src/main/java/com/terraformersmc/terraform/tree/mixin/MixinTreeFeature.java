package com.terraformersmc.terraform.tree.mixin;

import com.terraformersmc.terraform.tree.feature.ExtendedTreeGeneration;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;
import java.util.function.BiConsumer;

@Mixin(TreeFeature.class)
public abstract class MixinTreeFeature<FC extends FeatureConfig> extends Feature<FC> {
	// Bypasses the "no default constructor" error, will never actually get used
	private MixinTreeFeature() {
		super(null);
		throw new UnsupportedOperationException();
	}

	// TODO: Not sure if the redirect is better or worse here
	@Redirect(method = "Lnet/minecraft/world/gen/feature/TreeFeature;generate(Lnet/minecraft/world/StructureWorldAccess;Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;Ljava/util/function/BiConsumer;Ljava/util/function/BiConsumer;Lnet/minecraft/world/gen/feature/TreeFeatureConfig;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/AbstractBlock$AbstractBlockState;canPlaceAt(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z"))
	private boolean terrestria$allowSandyTreeGeneration(TestableWorld world, BlockPos pos, StructureWorldAccess world2, Random random, BlockPos pos2, BiConsumer<BlockPos, BlockState> trunkReplacer, BiConsumer<BlockPos, BlockState> foliageReplacer, TreeFeatureConfig config) {
		if ((this instanceof ExtendedTreeGeneration)) {
			return ((ExtendedTreeGeneration) this).canGenerateOn(world, pos);
		} else {
			return config.saplingProvider.getBlockState(random, pos).canPlaceAt(world2, pos);
		}
	}
}
