package com.terraformersmc.terraform.util;

import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

public class TerraformLargeSaplingGenerator extends LargeTreeSaplingGenerator {

	public final Supplier<TreeFeatureConfig> configSupplier;
	public final Supplier<TreeFeatureConfig> largeConfigSupplier;

	public TerraformLargeSaplingGenerator(Supplier<TreeFeatureConfig> configSupplier, Supplier<TreeFeatureConfig> largeConfigSupplier) {
		this.configSupplier = configSupplier;
		this.largeConfigSupplier = largeConfigSupplier;
	}

	@Nullable
	@Override
	protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
		return Feature.TREE.configure(configSupplier.get());
	}

	@Override
	protected ConfiguredFeature<TreeFeatureConfig, ?> createLargeTreeFeature(Random random) {
		return Feature.TREE.configure(largeConfigSupplier.get());
	}

}
