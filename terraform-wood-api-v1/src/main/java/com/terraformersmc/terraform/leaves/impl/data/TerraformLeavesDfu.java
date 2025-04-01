package com.terraformersmc.terraform.leaves.impl.data;

import com.terraformersmc.terraform.leaves.api.data.TerraformLeavesDfuApi;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;

public final class TerraformLeavesDfu {
	private static final Logger LOGGER = LoggerFactory.getLogger("terraform-leaves");

	private static final Collection<String> REGISTERED_EXTENDED_LEAVES = new HashSet<>();

	@SuppressWarnings("UnnecessaryReturnStatement")
	private TerraformLeavesDfu() {
		return;
	}

	public static void init() {
		REGISTERED_EXTENDED_LEAVES.clear();

		FabricLoader.getInstance().getEntrypointContainers("terraform-leaves-dfu", TerraformLeavesDfuApi.class).forEach(entrypoint -> {
			ModMetadata metadata = entrypoint.getProvider().getMetadata();
			String modId = metadata.getId();
			try {
				REGISTERED_EXTENDED_LEAVES.addAll(entrypoint.getEntrypoint().getDfuExtendedLeavesIds());
			} catch (Throwable e) {
				LOGGER.error("Mod {} provides a broken implementation of TerraformLeavesDfuApi", modId, e);
			}
		});
	}

	public static Collection<String> getRegisteredExtendedLeaves() {
		return REGISTERED_EXTENDED_LEAVES;
	}
}
