package com.terraformersmc.terraform.tree.feature;


import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;

/**
 * Interface to allow us to extend the generation options aside from what is given by vanilla
 */
public interface ExtendedTreeGeneration {
	boolean canGenerateOn(TestableWorld world, BlockPos pos);
}
