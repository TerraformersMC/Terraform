package com.terraformersmc.terraform.tree.placer;

import com.mojang.serialization.MapCodec;
import com.terraformersmc.terraform.tree.mixin.InvokerFoliagePlacerType;
import com.terraformersmc.terraform.tree.mixin.InvokerTrunkPlacerType;

import net.minecraft.util.Identifier;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class PlacerTypes {
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
