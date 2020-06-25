package com.terraformersmc.terraform.placer;

import com.mojang.serialization.Codec;
import com.terraformersmc.terraform.mixin.InvokerFoliagePlacerType;
import com.terraformersmc.terraform.mixin.InvokerTrunkPlacerType;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class PlacerTypes {
	public static <P extends FoliagePlacer> FoliagePlacerType<P> registerFoliagePlacer(String id, Codec<P> codec) {
		return InvokerFoliagePlacerType.callRegister(id, codec);
	}

	public static <P extends TrunkPlacer> TrunkPlacerType<P> registerTrunkPlacer(String id, Codec<P> codec) {
		return InvokerTrunkPlacerType.callRegister(id, codec);
	}
}
