package com.terraformersmc.terraform.biomeremapper.impl;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BiomeRemapper implements ModInitializer {
	public static final String MOD_ID = "terraform-biome-remapper";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Terraform Biome Remapper API present and accounted for.");
	}
}