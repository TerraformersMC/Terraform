package com.terraformersmc.terraform.leaves.api.data;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import com.terraformersmc.terraform.leaves.api.block.ExtendedLeavesBlock;
import com.terraformersmc.terraform.leaves.impl.data.TerraformLeavesDfu;
import java.util.Collection;
import net.minecraft.util.Mth;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.LeavesBlock;

public class TerraformExtendedDistanceFix extends DataFix {
	public TerraformExtendedDistanceFix(Schema schema, boolean changesType) {
		super(schema, changesType);
	}

	@Override
	public TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped("TerraformExtendedDistanceFix",
				this.getInputSchema().getType(References.BLOCK_STATE), blockStateTyped -> blockStateTyped
						.update(DSL.remainderFinder(), TerraformExtendedDistanceFix::updateExtendedLeavesDistance));
	}

	private static <T> Dynamic<T> updateExtendedLeavesDistance(Dynamic<T> blockStateDynamic) {
		Collection<String> extendedLeavesIds = TerraformLeavesDfu.getRegisteredExtendedLeaves();

		if (blockStateDynamic.get("Name").asString().result().filter(extendedLeavesIds::contains).isEmpty()) {
			return blockStateDynamic;
		}

		return blockStateDynamic.update("Properties", propertiesDynamic -> {
			int totalDistance = Integer.parseInt(propertiesDynamic.get("distance")
					.asString(Integer.toString(ExtendedLeavesBlock.MAX_TOTAL_DISTANCE)));

			propertiesDynamic = propertiesDynamic.set("distance",
					propertiesDynamic.createString(Integer.toString(Mth.clamp(
							totalDistance, 1, LeavesBlock.DECAY_DISTANCE))));

			propertiesDynamic = propertiesDynamic.set("extended_distance",
					propertiesDynamic.createString(Integer.toString(Mth.clamp(
							totalDistance - LeavesBlock.DECAY_DISTANCE, 0, ExtendedLeavesBlock.MAX_EXTENDED_DISTANCE))));

			return propertiesDynamic;
		});
	}
}
