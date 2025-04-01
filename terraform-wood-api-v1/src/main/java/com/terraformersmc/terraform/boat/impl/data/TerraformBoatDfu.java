package com.terraformersmc.terraform.boat.impl.data;

import com.terraformersmc.terraform.boat.api.data.TerraformBoatDfuApi;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;

public final class TerraformBoatDfu {
	private static final Logger LOGGER = LoggerFactory.getLogger("terraform-boat");

	private static final Collection<String> REGISTERED_BOATS = new HashSet<>();

	@SuppressWarnings("UnnecessaryReturnStatement")
	private TerraformBoatDfu() {
		return;
	}

	public static void init() {
		REGISTERED_BOATS.clear();

		FabricLoader.getInstance().getEntrypointContainers("terraform-boat-dfu", TerraformBoatDfuApi.class).forEach(entrypoint -> {
			ModMetadata metadata = entrypoint.getProvider().getMetadata();
			String modId = metadata.getId();
			try {
				REGISTERED_BOATS.addAll(entrypoint.getEntrypoint().getDfuBoatIds());
			} catch (Throwable e) {
				LOGGER.error("Mod {} provides a broken implementation of TerraformBoatDfuApi", modId, e);
			}
		});
	}

	public static Collection<String> getRegisteredBoats() {
		return REGISTERED_BOATS;
	}
}
