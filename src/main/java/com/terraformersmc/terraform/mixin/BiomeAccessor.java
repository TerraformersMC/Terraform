package com.terraformersmc.terraform.mixin;

import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

/**
 * An accessor for the mixed noise point values inside of biomes
 */
@Mixin(Biome.class)
public interface BiomeAccessor {
	@Accessor
	List<Biome.MixedNoisePoint> getNoisePoints();
}
