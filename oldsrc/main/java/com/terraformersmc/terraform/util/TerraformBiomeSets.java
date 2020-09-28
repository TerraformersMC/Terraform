package com.terraformersmc.terraform.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.world.biome.Biome;

public class TerraformBiomeSets {
	private static final Set<Biome> SLIME_SPAWN_BIOMES = new HashSet<>();

	public static Set<Biome> getSlimeSpawnBiomes() {
		return SLIME_SPAWN_BIOMES;
	}

	public static void addSlimeSpawnBiome(Biome biome) {
		SLIME_SPAWN_BIOMES.add(biome);
	}

	public static void addSlimeSpawnBiomes(Biome... biomes) {
		SLIME_SPAWN_BIOMES.addAll(Arrays.asList(biomes));
	}
}
