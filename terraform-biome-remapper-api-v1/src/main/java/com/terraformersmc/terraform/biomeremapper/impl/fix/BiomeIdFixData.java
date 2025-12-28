package com.terraformersmc.terraform.biomeremapper.impl.fix;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Map;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

public class BiomeIdFixData {
	@Nullable
	public static Int2ObjectArrayMap<Identifier> ACTIVE_BIOME_RAW_ID_MAP = null;

	public static void applyFabricDynamicRegistryMap(Map<Identifier, Object2IntMap<Identifier>> registryMap) {
		ACTIVE_BIOME_RAW_ID_MAP = new Int2ObjectArrayMap<>();
		for (Object2IntMap.Entry<Identifier> entry : registryMap.get(Registries.BIOME.identifier()).object2IntEntrySet()) {
			ACTIVE_BIOME_RAW_ID_MAP.put(entry.getIntValue(), entry.getKey());
		}
	}
}
