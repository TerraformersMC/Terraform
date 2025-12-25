package com.terraformersmc.terraform.boat.impl.data;

import com.mojang.datafixers.*;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.resources.Identifier;
import net.minecraft.util.datafix.ExtraDataFixUtils;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

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
		Optional<TerraformBoatDataImpl> boatData = TerraformBoatDataImpl.getOptional(Identifier.parse(type));
		String newId = null;

		if (boatData.isPresent()) {
			if (boatData.get().boatEntityType() != null) {
				newId = boatData.get().boatId().toString();
			} else if (boatData.get().raftEntityType() != null) {
				newId = boatData.get().raftId().toString();
			}
		}

		if (newId != null && TerraformBoatDfu.getRegisteredBoats().contains(newId)) {
			return newId;
		}

		return "minecraft:oak_boat";
	}

	private static String getNewChestBoatIdFromOldType(String type) {
		Optional<TerraformBoatDataImpl> boatData = TerraformBoatDataImpl.getOptional(Identifier.parse(type));
		String newId = null;

		if (boatData.isPresent()) {
			if (boatData.get().chestBoatEntityType() != null) {
				newId = boatData.get().chestBoatId().toString();
			} else if (boatData.get().chestRaftEntityType() != null) {
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
		OpticFinder<String> opticFinder = DSL.fieldFinder("id", NamespacedSchema.namespacedString());
		Type<?> type = this.getInputSchema().getType(References.ENTITY);
		Type<?> type2 = this.getOutputSchema().getType(References.ENTITY);

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

				return ExtraDataFixUtils.cast(type2, typed).update(DSL.remainderFinder(), dynamicx -> dynamicx.remove("TerraformBoat")).set(opticFinder, string);
			} else {
				return ExtraDataFixUtils.cast(type2, typed);
			}
		});
	}
}
