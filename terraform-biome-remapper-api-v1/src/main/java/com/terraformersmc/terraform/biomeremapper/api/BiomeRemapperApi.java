package com.terraformersmc.terraform.biomeremapper.api;

import com.google.common.collect.ImmutableMap;
import com.terraformersmc.terraform.biomeremapper.impl.BiomeRemappings;

/**
 * BiomeRemapperApi provides an interface to declare biomes to remap during data upgrades.
 *
 * To declare remappings, implement the interface in a public class and call the interface's register()
 * method once for each Minecraft data version requiring remappings at upgrade.  In most cases, mods will
 * only require one set of remappings (to the current version), but multiple sets can be needed in certain
 * situations.  For example, if the mod remaps mod:biome1 to mod:biome2 in 1.18 and then the mod also
 * remaps mod:biome3 to mod:biome1 in 1.19, two remappings may be required.
 */
public interface BiomeRemapperApi {

	/**
	 * The BiomeRemapperApi.init() method will be called by the biome remapper early during Minecraft
	 * start-up.  It must be safe to call before the implementing mod has been initialized.  When
	 * BiomeRemapperApi.init() is called it must immediately register() all required biome remappings.
	 * See the docs for register() for more details.
	 *
	 * To use the biome remapper API, something similar to the following must be added to fabric.mod.json
	 * (where com.something.yourmod.init.BiomeRemapperInit is the class implementing BiomeRemapperApi):
	 *
	 * <pre>{@code
	 * "entrypoints": {
     *   "terraform-biome-remapper": [
     *     "com.something.yourmod.init.BiomeRemapperInit"
     *   ]
     * }
	 * }</pre>
	 */
	void init();

	/**
	 * Use BiomeRemapperApi.register() to register remapping maps with the biome remapper.  This must be
	 * done in the BiomeRemapperApi implementor's init() method.
	 *
	 * The modId should be the mod's identifier string, and the dataVersion should be a Minecraft data version.
	 * Convenience variables for recent versions of Minecraft are available in the DataVersions class.
	 *
	 * The remapping is an immutable map of pairs of String values.  In each pair, the first value is the old
	 * biome identifier string and the second value is the new biome identifier string.
	 * For example, "traverse:mini_jungle" -> "minecraft:jungle".
	 *
	 * <pre>{@code
	 * @Override
	 * public void init() {
	 * 		register(Traverse.MOD_ID, DataVersions.V_1_18_2,
	 * 			ImmutableMap.<String, String>builder()
	 * 				.put("traverse:meadow", "traverse:flatlands")
	 * 				.put("traverse:mini_jungle", "minecraft:jungle")
	 * 				.build()
	 * 		);
	 * }
	 * }</pre>
	 *
	 * @param modId String - The ID of the mod registering the remapping.
	 * @param dataVersion int - The Minecraft data version the remapping targets.
	 * @param remapping ImmutableMap<String, String> - The map from old biome ID to new biome ID.
	 */
	default void register(String modId, int dataVersion, ImmutableMap<String, String> remapping) {
		BiomeRemappings.register(modId, dataVersion, remapping);
	}
}
