package com.terraformersmc.terraform.util;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TerraformBiomeSets {
	private static final Set<RegistryKey<Biome>> SLIME_SPAWN_BIOMES = new HashSet<>();

	public static Set<RegistryKey<Biome>> getSlimeSpawnBiomes() {
		return SLIME_SPAWN_BIOMES;
	}

	public static void addSlimeSpawnBiome(RegistryKey<Biome> biome) {
		SLIME_SPAWN_BIOMES.add(biome);
	}

	@SafeVarargs
	public static void addSlimeSpawnBiomes(RegistryKey<Biome>... biomes) {
		SLIME_SPAWN_BIOMES.addAll(Arrays.asList(biomes));
	}
}
