package com.terraformersmc.terraform.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.OakTreeFeature;

import java.util.function.Function;

public class TerraformOakTreeFeature extends OakTreeFeature {

	public TerraformOakTreeFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactory, boolean emitNeighborBlockUpdates) {
		super(configFactory, emitNeighborBlockUpdates);
	}

	public TerraformOakTreeFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactory, boolean emitNeighborBlockUpdates, int minHeight, BlockState log, BlockState leaves, boolean vines) {
		super(configFactory, emitNeighborBlockUpdates, minHeight, log, leaves, vines);
	}

	public TerraformOakTreeFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactory, boolean emitNeighborBlockUpdates, int minHeight, BlockState log, BlockState leaves) {
		super(configFactory, emitNeighborBlockUpdates, minHeight, log, leaves, false);
	}

	public TerraformOakTreeFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactory, boolean emitNeighborBlockUpdates, BlockState log, BlockState leaves) {
		super(configFactory, emitNeighborBlockUpdates, 4, log, leaves, false);
	}

}
