package com.terraformersmc.terraform.biomeremapper.impl;

import com.google.common.collect.ImmutableMap;
import com.terraformersmc.terraform.biomeremapper.api.BiomeRemapperApi;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;

import java.util.Hashtable;

public class BiomeRemappings {
	public static final Hashtable<String, RemappingRecord> BIOME_REMAPPING_REGISTRY = new Hashtable<>(8);

	public static void invokeEndpoints() {
		FabricLoader.getInstance().getEntrypointContainers("terraform-biome-remapper", BiomeRemapperApi.class).forEach(entrypoint -> {
			ModMetadata metadata = entrypoint.getProvider().getMetadata();
			String modId = metadata.getId();
			try {
				entrypoint.getEntrypoint().init();
			} catch (Throwable e) {
				BiomeRemapper.LOGGER.error("Mod {} provides a broken implementation of BiomeRemapperApi", modId, e);
			}
		});

	}

	public static void register(String modId, int dataVersion, ImmutableMap<String, String> remapping) {
		String key = dataVersion + "_" + modId;
		if (BIOME_REMAPPING_REGISTRY.containsKey(key)) {
			BiomeRemapper.LOGGER.warn("Ignored duplicate remapping: " + key);
		} else {
			BiomeRemapper.LOGGER.info("Added remapping: " + key);
			BIOME_REMAPPING_REGISTRY.put(key, new RemappingRecord(modId, dataVersion, remapping));
		}
	}

	public record RemappingRecord(String modId, int dataVersion, ImmutableMap<String, String> remapping) {}
}
