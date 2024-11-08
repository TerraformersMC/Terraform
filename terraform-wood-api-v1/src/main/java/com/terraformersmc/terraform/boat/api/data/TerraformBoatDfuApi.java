package com.terraformersmc.terraform.boat.api.data;

import java.util.Collection;

public interface TerraformBoatDfuApi {

	/**
	 * The purpose of this API is to allow pre-1.21.2 Terraform API boats to be converted to 1.21.2 and above.
	 * Properly registered boats which are submitted to this API will retain their existing wood type.  Any
	 * Terraform API boats which are not submitted to this API will be upgraded to oak boat types instead.
	 *
	 * The TerraformBoatDfuApi.getDfuBoatIds() method will be called by the boat DFU code early during
	 * Minecraft start-up.  It must be safe to call before the implementing mod has been initialized.  When
	 * TerraformBoatDfuApi.getDfuBoatIds() is called it should return a collection of string-form identifiers
	 * -- f.e. {@code Set.of("yourmod:mahogany_boat", "yourmod:mahogany_chest_boat")} -- for all modded boats
	 * which should be tracked by the DFU.  Identifiers must be post-migration (not, f.e., "terraform:boat"
	 * or "yourmod:mahogany").  Any boats listed must be registered during mod initialization.
     *
	 * To use the boat DFU API, something similar to the following must be added to fabric.mod.json
	 * (where com.something.yourmod.init.TerraformBoatDfu is the class implementing TerraformBoatDfuApi):
	 *
	 * <pre>{@code
	 * "entrypoints": {
	 *   "terraform-boat-dfu": [
	 *     "com.something.yourmod.init.TerraformBoatDfu"
	 *   ]
	 * }
	 * }</pre>
	 *
	 * @return Collection of string values of boat type identifiers
	 */
	Collection<String> getDfuBoatIds();
}
