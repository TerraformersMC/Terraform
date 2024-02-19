package com.terraformersmc.terraform.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fabricmc.loader.api.FabricLoader;

@SuppressWarnings("unused")
public class BiomeConfigHandler {

	private final String namespace;
	private File file;
	private BiomeConfig config;

	public BiomeConfigHandler(String namespace) {
		this.namespace = namespace;
	}

	private void prepareBiomeConfigFile() {
		if (file != null) {
			return;
		}

		File configDirectory = new File(FabricLoader.getInstance().getConfigDir().toFile(), namespace);

		if (configDirectory.isDirectory() || configDirectory.mkdir()) {
			file = new File(configDirectory, "biomes.json");
		} else {
			System.err.println("Couldn't create config directory for " + namespace);
		}
	}

	public BiomeConfig getBiomeConfig() {
		return getBiomeConfig(false);
	}

	public BiomeConfig getBiomeConfig(boolean defaultFreeze) {
		if (config != null) {
			return config;
		}

		config = new BiomeConfig(defaultFreeze);
		load();

		return config;
	}

	private void load() {
		prepareBiomeConfigFile();

		try {
			if (file.exists()) {
				Gson gson = new Gson();
				BufferedReader br = new BufferedReader(new FileReader(file));

				config = gson.fromJson(br, BiomeConfig.class);
			}
		} catch (Exception e) {
			System.err.println("Couldn't load biome configuration file for " + namespace + ", reverting to defaults");
			e.printStackTrace();
		}
	}

	public void save() {
		prepareBiomeConfigFile();

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonString = gson.toJson(config);

		try (FileWriter fileWriter = new FileWriter(file)) {
			fileWriter.write(jsonString);
		} catch (Exception e) {
			System.err.println("Couldn't save biome configuration file for " + namespace);
			e.printStackTrace();
		}
	}
}
