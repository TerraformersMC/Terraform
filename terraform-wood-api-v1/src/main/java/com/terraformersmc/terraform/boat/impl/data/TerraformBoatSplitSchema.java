package com.terraformersmc.terraform.boat.impl.data;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

import java.util.Map;
import java.util.function.Supplier;

public class TerraformBoatSplitSchema extends IdentifierNormalizingSchema {
	public TerraformBoatSplitSchema(int versionKey, Schema parent) {
		super(versionKey, parent);
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map = super.registerEntities(schema);

		map.remove("terraform:boat");
		map.remove("terraform:chest_boat");

		for (TerraformBoatData boatData : TerraformBoatData.getCollection()) {
			if (boatData.boatEntity() != null) {
				schema.registerSimple(map, boatData.boatId().toString());
			}
			if (boatData.chestBoatEntity() != null) {
				schema.register(map, boatData.chestBoatId().toString(), string -> DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(schema))));
			}
			if (boatData.raftEntity() != null) {
				schema.registerSimple(map, boatData.raftId().toString());
			}
			if (boatData.chestRaftEntity() != null) {
				schema.register(map, boatData.chestRaftId().toString(), string -> DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(schema))));
			}
		}

		return map;
	}
}
