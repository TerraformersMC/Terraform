package com.terraformersmc.terraform.overworldbiomes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import com.terraformersmc.terraform.overworldbiomes.mixin.BuiltinBiomesAccessor;

import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class OverworldBiomesExt {
	private static Map<RegistryKey<Biome>, RegistryKey<Biome>> OVERWORLD_BORDER_MAP = new HashMap<>();
	private static Map<RegistryKey<Biome>, RegistryKey<Biome>> OVERWORLD_CENTER_MAP = new HashMap<>();
	private static Map<RegistryKey<Biome>, List<PredicatedBiomeEntry>> PREDICATED_BORDER_MAP = new HashMap<>();

	private static RegistryKey<Biome> sanitize(RegistryKey<Biome> biome) {
		Objects.requireNonNull(biome);

		int rawId = BuiltinRegistries.BIOME.getRawId(BuiltinRegistries.BIOME.get(biome));

		BuiltinBiomesAccessor.getIdMap().computeIfAbsent(rawId, key -> biome);

		return biome;
	}

	public static void addBorderBiome(RegistryKey<Biome> biome, RegistryKey<Biome> border) {
		OVERWORLD_BORDER_MAP.put(sanitize(biome), sanitize(border));
	}

	public static Optional<RegistryKey<Biome>> getBorder(RegistryKey<Biome> biome) {
		return Optional.ofNullable(OVERWORLD_BORDER_MAP.get(biome));
	}

	public static void addCenterBiome(RegistryKey<Biome> biome, RegistryKey<Biome> border) {
		OVERWORLD_CENTER_MAP.put(sanitize(biome), sanitize(border));
	}

	public static Optional<RegistryKey<Biome>> getCenter(RegistryKey<Biome> biome) {
		return Optional.ofNullable(OVERWORLD_CENTER_MAP.get(biome));
	}

	public static final List<PredicatedBiomeEntry> getPredicatedBorders(RegistryKey<Biome> biome) {
		return PREDICATED_BORDER_MAP.getOrDefault(biome, new ArrayList<>());
	}

	public static void addPredicatedBorderBiome(RegistryKey<Biome> biomeBase, RegistryKey<Biome> biomeBorder, Predicate<RegistryKey<Biome>> predicate) {
		PREDICATED_BORDER_MAP.computeIfAbsent(sanitize(biomeBase), biome -> new ArrayList<>()).add(new PredicatedBiomeEntry(sanitize(biomeBorder), predicate));
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
