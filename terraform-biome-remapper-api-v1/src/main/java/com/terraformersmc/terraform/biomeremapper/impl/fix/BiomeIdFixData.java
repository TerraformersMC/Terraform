package com.terraformersmc.terraform.biomeremapper.impl.fix;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.Map;

public class BiomeIdFixData {
	public static Int2ObjectArrayMap<Identifier> ACTIVE_BIOME_RAW_ID_MAP = null;

	public static void applyFabricDynamicRegistryMap(Map<Identifier, Object2IntMap<Identifier>> registryMap) {
		ACTIVE_BIOME_RAW_ID_MAP = new Int2ObjectArrayMap<>();
		for (Object2IntMap.Entry<Identifier> entry : registryMap.get(RegistryKeys.BIOME.getValue()).object2IntEntrySet()) {
			ACTIVE_BIOME_RAW_ID_MAP.put(entry.getIntValue(), entry.getKey());
		}
	}
}
