package com.terraformersmc.terraform.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FoliagePlacerType.class)
public interface InvokerFoliagePlacerType {
	@Invoker
	static <P extends FoliagePlacer> FoliagePlacerType<P> callMethod_28850(String string, Codec<P> codec) {
		throw new UnsupportedOperationException();
	}
}
