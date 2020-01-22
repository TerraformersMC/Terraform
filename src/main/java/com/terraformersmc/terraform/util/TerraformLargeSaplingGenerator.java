package com.terraformersmc.terraform.util;

import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

public class TerraformLargeSaplingGenerator extends LargeTreeSaplingGenerator {

	public final Supplier<AbstractTreeFeature<BranchedTreeFeatureConfig>> featureSupplier;
	public final Supplier<BranchedTreeFeatureConfig> configSupplier;
	public final Supplier<AbstractTreeFeature<MegaTreeFeatureConfig>> largeFeatureSupplier;
	public final Supplier<MegaTreeFeatureConfig> largeConfigSupplier;

	public TerraformLargeSaplingGenerator(Supplier<AbstractTreeFeature<BranchedTreeFeatureConfig>> featureSupplier, Supplier<BranchedTreeFeatureConfig> configSupplier, Supplier<AbstractTreeFeature<MegaTreeFeatureConfig>> largeFeatureSupplier, Supplier<MegaTreeFeatureConfig> largeConfigSupplier) {
		this.featureSupplier = featureSupplier;
		this.configSupplier = configSupplier;
		this.largeFeatureSupplier = largeFeatureSupplier;
		this.largeConfigSupplier = largeConfigSupplier;
	}

	@Nullable
	@Override
	protected ConfiguredFeature<BranchedTreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
		return featureSupplier.get().configure(configSupplier.get());
	}

	@Override
	protected ConfiguredFeature<MegaTreeFeatureConfig, ?> createLargeTreeFeature(Random random) {
		return largeFeatureSupplier.get().configure(largeConfigSupplier.get());
	}

}
