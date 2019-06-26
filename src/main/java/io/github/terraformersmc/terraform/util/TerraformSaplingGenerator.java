package io.github.terraformersmc.terraform.util;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import java.util.Random;
import java.util.function.Supplier;

public class TerraformSaplingGenerator extends SaplingGenerator {
	public final Supplier<AbstractTreeFeature<DefaultFeatureConfig>> featureSupplier;

	public TerraformSaplingGenerator(Supplier<AbstractTreeFeature<DefaultFeatureConfig>> featureSupplier) {
		this.featureSupplier = featureSupplier;
	}

	@Override
	protected AbstractTreeFeature<DefaultFeatureConfig> createTreeFeature(Random random) {
		return featureSupplier.get();
	}
}
