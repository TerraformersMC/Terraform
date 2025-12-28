package com.terraformersmc.terraform.leaves.api.data;

import java.util.Collection;

public interface TerraformLeavesDfuApi {
	/**
	 * <p>
	 * The purpose of this API is to allow pre-1.21.5 Terraform API extended leaves to be converted to 1.21.5
	 * and above.  Extended leaves blocks which are submitted to this API will retain extended distance values.
	 * Any Terraform API extended leaves which are not submitted to this API are likely to break when upgrading
	 * block states with a distance value above 6. (Some of the leaves will naturally decay.)
	 * </p><p>
	 * The TerraformLeavesDfuApi.getDfuExtendedLeavesIds() method will be called by the leaves DFU code early
	 * during Minecraft start-up.  It must be safe to call before the implementing mod has been initialized.
	 * When TerraformLeavesDfuApi.getDfuExtendedLeavesIds() is called it should return a collection of string-form
	 * identifiers -- f.e. {@code Set.of("yourmod:mahogany_leaves", "yourmod:dark_mahogany_leaves")} -- for all
	 * extended leaves which should be fixed by the DFU.
	 * </p>
	 * To use the leaves DFU API, something similar to the following must be added to fabric.mod.json
	 * (where com.something.yourmod.init.TerraformLeavesDfu is the class implementing TerraformLeavesDfuApi):
	 *
	 * <pre>{@code
	 * "entrypoints": {
	 *   "terraform-leaves-dfu": [
	 *     "com.something.yourmod.init.TerraformLeavesDfu"
	 *   ]
	 * }
	 * }</pre>
	 *
	 * @return Collection of string values of extended leaves identifiers
	 */
	Collection<String> getDfuExtendedLeavesIds();
}
