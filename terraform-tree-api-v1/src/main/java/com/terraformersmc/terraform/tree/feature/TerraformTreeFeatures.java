package com.terraformersmc.terraform.tree.feature;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class TerraformTreeFeatures {
	public static Feature<TreeFeatureConfig> SANDY_TREE;

	public static void init() {
		SANDY_TREE = register("sandy_tree", new SandyTreeFeature(TreeFeatureConfig.CODEC));
	}

	public static <T extends Feature<FC>, FC extends FeatureConfig> T register(String name, T feature) {
		return Registry.register(Registry.FEATURE, new Identifier("terraform", name), feature);
	}
}
