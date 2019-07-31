package com.terraformersmc.terraform.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class BiomeConfigHandler {

	private String namespace;
	private File file;
	private BiomeConfig config;

	public BiomeConfigHandler(String namespace) {
		this.namespace = namespace;
	}

	private void prepareBiomeConfigFile() {
		if (file != null) {
			return;
		}

		File configDirectory = new File(FabricLoader.getInstance().getConfigDirectory(), namespace);
		file = new File(configDirectory, "biomes.json");

		if (!configDirectory.exists()) {
			configDirectory.mkdir();
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
		} catch (IOException e) {
			System.err.println("Couldn't save biome configuration file for " + namespace);
			e.printStackTrace();
		}
	}
}
