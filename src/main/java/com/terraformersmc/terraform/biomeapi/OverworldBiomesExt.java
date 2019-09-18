package com.terraformersmc.terraform.biomeapi;

import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class OverworldBiomesExt {
	private static Map<Biome, Biome> OVERWORLD_BORDER_MAP = new HashMap<>();
	private static Map<Biome, Biome> OVERWORLD_CENTER_MAP = new HashMap<>();

	public static void addBorderBiome(Biome biome, Biome border) {
		OVERWORLD_BORDER_MAP.put(Objects.requireNonNull(biome), Objects.requireNonNull(border));
	}

	public static Optional<Biome> getBorder(Biome biome) {
		return Optional.ofNullable(OVERWORLD_BORDER_MAP.get(biome));
	}

	public static void addCenterBiome(Biome biome, Biome border) {
		OVERWORLD_CENTER_MAP.put(Objects.requireNonNull(biome), Objects.requireNonNull(border));
	}

	public static Optional<Biome> getCenter(Biome biome) {
		return Optional.ofNullable(OVERWORLD_CENTER_MAP.get(biome));
	}
	
	public static final class PredicatedBiomeEntry {
		public final Biome biome;
		public final Predicate<Biome> predicate;
		
		PredicatedBiomeEntry(Biome b, Predicate<Biome> p) {
			biome = b;
			predicate = p;
		}
	}

	public static List<PredicatedBiomeEntry> getLargeEdges(Biome centre) {
		// TODO Auto-generated method stub
		return null;
	}
}
