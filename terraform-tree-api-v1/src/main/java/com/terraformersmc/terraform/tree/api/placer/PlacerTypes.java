package com.terraformersmc.terraform.tree.api.placer;

import com.mojang.serialization.MapCodec;
import com.terraformersmc.terraform.tree.impl.mixin.InvokerFoliagePlacerType;
import com.terraformersmc.terraform.tree.impl.mixin.InvokerTrunkPlacerType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public final class PlacerTypes {
	private PlacerTypes() {
		return;
	}

	// Deprecated annotation is just a warning for non-internal references
	@SuppressWarnings("deprecation")
	public static <P extends FoliagePlacer> FoliagePlacerType<P> registerFoliagePlacer(String id, MapCodec<P> codec) {
		return InvokerFoliagePlacerType.callRegister(id, codec);
	}

	// Deprecated annotation is just a warning for non-internal references
	@SuppressWarnings("deprecation")
	public static <P extends TrunkPlacer> TrunkPlacerType<P> registerTrunkPlacer(String id, MapCodec<P> codec) {
		return InvokerTrunkPlacerType.callRegister(id, codec);
	}

	public static <P extends FoliagePlacer> FoliagePlacerType<P> registerFoliagePlacer(Identifier id, MapCodec<P> codec) {
		return registerFoliagePlacer(id.toString(), codec);
	}

	public static <P extends TrunkPlacer> TrunkPlacerType<P> registerTrunkPlacer(Identifier id, MapCodec<P> codec) {
		return registerTrunkPlacer(id.toString(), codec);
	}
}
