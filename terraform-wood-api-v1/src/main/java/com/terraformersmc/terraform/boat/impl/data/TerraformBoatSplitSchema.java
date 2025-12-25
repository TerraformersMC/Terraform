package com.terraformersmc.terraform.boat.impl.data;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class TerraformBoatSplitSchema extends NamespacedSchema {
	public TerraformBoatSplitSchema(int versionKey, Schema parent) {
		super(versionKey, parent);
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map = super.registerEntities(schema);

		map.remove("terraform:boat");
		map.remove("terraform:chest_boat");

		Collection<String> registeredBoats = TerraformBoatDfu.getRegisteredBoats();
		for (String boat : registeredBoats) {
			if (boat.contains("_chest") || boat.contains("chest_")) {
				schema.register(map, boat, string -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in(schema))));
			} else {
				schema.registerSimple(map, boat);
			}
		}

		return map;
	}
}
