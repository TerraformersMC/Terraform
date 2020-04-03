package com.terraformersmc.terraform.util;

import java.util.Random;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class TerraformSaplingGenerator extends SaplingGenerator {
	public final Supplier<AbstractTreeFeature<BranchedTreeFeatureConfig>> featureSupplier;
	public final Supplier<BranchedTreeFeatureConfig> configSupplier;

	public TerraformSaplingGenerator(Supplier<AbstractTreeFeature<BranchedTreeFeatureConfig>> featureSupplier, Supplier<BranchedTreeFeatureConfig> configSupplier) {
		this.featureSupplier = featureSupplier;
		this.configSupplier = configSupplier;
	}

	@Nullable
	@Override
	protected ConfiguredFeature<BranchedTreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
		return featureSupplier.get().configure(configSupplier.get());
	}
}
