package com.terraformersmc.terraform.boat.impl.data;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class TerraformAddBoatsSchema extends NamespacedSchema {
	public TerraformAddBoatsSchema(int versionKey, Schema parent) {
		super(versionKey, parent);
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map = super.registerEntities(schema);

		schema.registerSimple(map, "terraform:boat");
		schema.register(map, "terraform:chest_boat", string -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in(schema))));

		return map;
	}
}
