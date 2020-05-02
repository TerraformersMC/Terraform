package com.terraformersmc.terraform.mixin;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Function;

@Mixin(FoliagePlacerType.class)
public interface InvokerFoliagePlacerType {
	@Invoker
	static <P extends FoliagePlacer> FoliagePlacerType<P> callRegister(String id, Function<Dynamic<?>, P> deserializer) {
		return null;
	}
}
