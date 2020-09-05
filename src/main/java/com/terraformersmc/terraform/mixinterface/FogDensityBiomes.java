package com.terraformersmc.terraform.mixinterface;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @author <Wtoll> Will Toll on 2020-05-24
 * @project Cinderscapes
 */
public final class FogDensityBiomes {

	private static final Map<RegistryKey<Biome>, Float> REGISTRY = new IdentityHashMap<>();

	private FogDensityBiomes() {
	}

	public static void setFogMultiplier(RegistryKey<Biome> biome, float fogMultiplier) {
		REGISTRY.put(biome, fogMultiplier);
	}

	public static boolean hasFogMultiplier(RegistryKey<Biome> biome) {
		return REGISTRY.containsKey(biome);
	}

	public static Float getFogMultiplier(RegistryKey<Biome> biome, Float defaultValue) {
		return REGISTRY.getOrDefault(biome, defaultValue);
	}

}
