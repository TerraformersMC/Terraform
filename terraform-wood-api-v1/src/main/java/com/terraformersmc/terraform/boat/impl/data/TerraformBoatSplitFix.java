package com.terraformersmc.terraform.boat.impl.data;

import com.mojang.datafixers.*;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.FixUtil;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class TerraformBoatSplitFix extends DataFix {
	public TerraformBoatSplitFix(Schema outputSchema, boolean changesType) {
		super(outputSchema, changesType);
	}

	private static boolean isBoat(String id) {
		return id.equals("terraform:boat");
	}

	private static boolean isChestBoat(String id) {
		return id.equals("terraform:chest_boat");
	}

	private static boolean isBoatOrChestBoat(String id) {
		return isBoat(id) || isChestBoat(id);
	}

	private static String getNewBoatIdFromOldType(String type) {
		Optional<TerraformBoatData> boatData = TerraformBoatData.getOptional(Identifier.of(type));
		String newId = null;

		if (boatData.isPresent()) {
			if (boatData.get().boatEntity() != null) {
				newId = boatData.get().boatId().toString();
			} else if (boatData.get().raftEntity() != null) {
				newId = boatData.get().raftId().toString();
			}
		}

		if (newId != null && TerraformBoatDfu.getRegisteredBoats().contains(newId)) {
			return newId;
		}

		return "minecraft:oak_boat";
	}

	private static String getNewChestBoatIdFromOldType(String type) {
		Optional<TerraformBoatData> boatData = TerraformBoatData.getOptional(Identifier.of(type));
		String newId = null;

		if (boatData.isPresent()) {
			if (boatData.get().chestBoatEntity() != null) {
				newId = boatData.get().chestBoatId().toString();
			} else if (boatData.get().chestRaftEntity() != null) {
				newId = boatData.get().chestRaftId().toString();
			}
		}

		if (newId != null && TerraformBoatDfu.getRegisteredBoats().contains(newId)) {
			return newId;
		}

		return "minecraft:oak_chest_boat";
	}

	@Override
	protected TypeRewriteRule makeRule() {
		OpticFinder<String> opticFinder = DSL.fieldFinder("id", IdentifierNormalizingSchema.getIdentifierType());
		Type<?> type = this.getInputSchema().getType(TypeReferences.ENTITY);
		Type<?> type2 = this.getOutputSchema().getType(TypeReferences.ENTITY);

		return this.fixTypeEverywhereTyped("TerraformBoatSplitFix", type, type2, typed -> {
			Optional<String> optional = typed.getOptional(opticFinder);

			if (optional.isPresent() && isBoatOrChestBoat(optional.get())) {
				Dynamic<?> dynamic = typed.getOrCreate(DSL.remainderFinder());
				Optional<String> optional2 = dynamic.get("TerraformBoat").asString().result();

				String string;
				if (isChestBoat(optional.get())) {
					string = optional2.map(TerraformBoatSplitFix::getNewChestBoatIdFromOldType).orElse("minecraft:oak_chest_boat");
				} else {
					string = optional2.map(TerraformBoatSplitFix::getNewBoatIdFromOldType).orElse("minecraft:oak_boat");
				}

				return FixUtil.withType(type2, typed).update(DSL.remainderFinder(), dynamicx -> dynamicx.remove("TerraformBoat")).set(opticFinder, string);
			} else {
				return FixUtil.withType(type2, typed);
			}
		});
	}
}
