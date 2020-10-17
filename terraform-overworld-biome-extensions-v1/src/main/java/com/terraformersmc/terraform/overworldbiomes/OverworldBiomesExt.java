package com.terraformersmc.terraform.overworldbiomes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class OverworldBiomesExt {
	private static Map<RegistryKey<Biome>, RegistryKey<Biome>> OVERWORLD_BORDER_MAP = new HashMap<>();
	private static Map<RegistryKey<Biome>, RegistryKey<Biome>> OVERWORLD_CENTER_MAP = new HashMap<>();
	private static Map<RegistryKey<Biome>, List<PredicatedBiomeEntry>> PREDICATED_BORDER_MAP = new HashMap<>();

	public static void addBorderBiome(RegistryKey<Biome> biome, RegistryKey<Biome> border) {
		OVERWORLD_BORDER_MAP.put(Objects.requireNonNull(biome), Objects.requireNonNull(border));
	}

	public static Optional<RegistryKey<Biome>> getBorder(RegistryKey<Biome> biome) {
		return Optional.ofNullable(OVERWORLD_BORDER_MAP.get(biome));
	}

	public static void addCenterBiome(RegistryKey<Biome> biome, RegistryKey<Biome> border) {
		OVERWORLD_CENTER_MAP.put(Objects.requireNonNull(biome), Objects.requireNonNull(border));
	}

	public static Optional<RegistryKey<Biome>> getCenter(RegistryKey<Biome> biome) {
		return Optional.ofNullable(OVERWORLD_CENTER_MAP.get(biome));
	}

	public static final List<PredicatedBiomeEntry> getPredicatedBorders(RegistryKey<Biome> biome) {
		return PREDICATED_BORDER_MAP.getOrDefault(biome, new ArrayList<>());
	}

	public static void addPredicatedBorderBiome(RegistryKey<Biome> biomeBase, RegistryKey<Biome> biomeBorder, Predicate<RegistryKey<Biome>> predicate) {
		PREDICATED_BORDER_MAP.computeIfAbsent(biomeBase, biome -> new ArrayList<>()).add(new PredicatedBiomeEntry(biomeBorder, predicate));
	}

	public static final class PredicatedBiomeEntry {
		public final RegistryKey<Biome> biome;
		public final Predicate<RegistryKey<Biome>> predicate;

		PredicatedBiomeEntry(RegistryKey<Biome> b, Predicate<RegistryKey<Biome>> p) {
			biome = b;
			predicate = p;
		}
	}
}
