package com.terraformersmc.terraform.util;

import com.terraformersmc.terraform.Terraform;
import com.terraformersmc.terraform.mixin.BiomeAccessor;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that checks if two biomes are trying to register with the same noise values and throws a warning
 */
public class NoiseCollisionChecker {
	/**
	 * A map that contains hashes of mixed noise point values registered by the identifier of the biomes they relate to
	 */
	private static final Map<Identifier, Long> NOISE_HASHES = new HashMap<>();

	/**
	 * A static variable to determine whether or not the initialize method has been called before
	 */
	private static boolean firstCall = true;

	/**
	 * The main initialization method used by mods to ensure noise point values are checked
	 */
	public static void onInitialize() {
		if (firstCall) {
			Registry.BIOME.getIds().forEach((id) -> checkBiome(id, Registry.BIOME.get(id)));
			RegistryEntryAddedCallback.event(Registry.BIOME).register((index, id, biome) -> checkBiome(id, biome));
		}
		firstCall = false;
	}

	/**
	 * Checks to see if the noise point of a biome collides with another
	 * @param id The identifier of the biome in question
	 * @param biome The biome object in question
	 */
	private static void checkBiome(Identifier id, Biome biome) {
		long hash = ((BiomeAccessor) biome).getNoisePoints().stream().mapToInt(Biome.MixedNoisePoint::hashCode).sum();
		NOISE_HASHES.forEach((biomeId, noiseHash) -> {
			if (noiseHash.equals(hash)) {
				Terraform.LOGGER.warning(String.format("Biome %s and biome %s share the same mixed noise point value. This will result in one eclipsing the other in generation. Please notify the mod authors immediately.", biomeId, id));
			}
		});
		NOISE_HASHES.put(id, hash);
	}
}
