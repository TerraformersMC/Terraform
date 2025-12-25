package com.terraformersmc.terraform.tree.impl.mixin;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TrunkPlacerType.class)
public interface InvokerTrunkPlacerType {
	@Invoker
	@Deprecated
	static <P extends TrunkPlacer> TrunkPlacerType<P> callRegister(String id, MapCodec<P> codec) {
		throw new UnsupportedOperationException();
	}
}
