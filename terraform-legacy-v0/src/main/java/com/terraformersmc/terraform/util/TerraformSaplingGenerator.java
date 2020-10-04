package com.terraformersmc.terraform.util;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

public class TerraformSaplingGenerator extends SaplingGenerator {
	public final Supplier<TreeFeatureConfig> configSupplier;

	public TerraformSaplingGenerator(Supplier<TreeFeatureConfig> configSupplier) {
		this.configSupplier = configSupplier;
	}

	@Nullable
	@Override
	protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
		return Feature.TREE.configure(configSupplier.get());
	}
}
