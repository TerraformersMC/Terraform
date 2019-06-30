package io.github.terraformersmc.terraform.util;

import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import java.util.Random;
import java.util.function.Supplier;

public class TerraformLargeSaplingGenerator extends LargeTreeSaplingGenerator {

    public final Supplier<AbstractTreeFeature<DefaultFeatureConfig>> featureSupplier;
    public final Supplier<AbstractTreeFeature<DefaultFeatureConfig>> largeFeatureSupplier;

    public TerraformLargeSaplingGenerator(Supplier<AbstractTreeFeature<DefaultFeatureConfig>> featureSupplier, Supplier<AbstractTreeFeature<DefaultFeatureConfig>> largeFeatureSupplier) {
        this.featureSupplier = featureSupplier;
        this.largeFeatureSupplier = largeFeatureSupplier;
    }

    @Override
    protected AbstractTreeFeature<DefaultFeatureConfig> createTreeFeature(Random random) {
        return featureSupplier.get();
    }

    @Override
    protected AbstractTreeFeature<DefaultFeatureConfig> createLargeTreeFeature(Random random) {
        return largeFeatureSupplier.get();
    }

}
