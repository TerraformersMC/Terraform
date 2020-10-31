package com.terraformersmc.terraform.overworldbiomes.mixin;

import java.util.Optional;
import java.util.function.IntConsumer;

import com.terraformersmc.terraform.overworldbiomes.OverworldBiomesExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.EaseBiomeEdgeLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

@Mixin(EaseBiomeEdgeLayer.class)
@SuppressWarnings("unused") // it's a mixin
public class MixinEaseBiomeEdgeLayer {
	@Inject(method = "sample", at = @At("HEAD"), cancellable = true)
	public void onSample(LayerRandomnessSource rand, int neighbor1, int neighbor2, int neighbor3, int neighbor4, int center, CallbackInfoReturnable<Integer> info) {
		//predicated borders
		RegistryKey<Biome> biome = fromRawId(center);

		for (OverworldBiomesExt.PredicatedBiomeEntry entry : OverworldBiomesExt.getPredicatedBorders(biome)) {
			if (entry.predicate.test(fromRawId(neighbor1)) || entry.predicate.test(fromRawId(neighbor2)) || entry.predicate.test(fromRawId(neighbor3)) || entry.predicate.test(fromRawId(neighbor4))) {
				info.setReturnValue(toRawId(entry.biome));
			}
		}

		//border biomes
		boolean replaced =
				tryReplace(center, neighbor1, info::setReturnValue) ||
						tryReplace(center, neighbor2, info::setReturnValue) ||
						tryReplace(center, neighbor3, info::setReturnValue) ||
						tryReplace(center, neighbor4, info::setReturnValue);

		if (replaced) {
			return;
		}

		//center biomes
		if (surrounded(neighbor1, neighbor2, neighbor3, neighbor4, center)) {
			Optional<RegistryKey<Biome>> target = OverworldBiomesExt.getCenter(fromRawId(center));

			target.ifPresent(value -> info.setReturnValue(toRawId(value)));
		}
	}

	private static boolean tryReplace(int center, int neighbor, IntConsumer consumer) {
		if (center == neighbor) {
			return false;
		}

		Optional<RegistryKey<Biome>> border = OverworldBiomesExt.getBorder(fromRawId(neighbor));
		if (border.isPresent()) {
			consumer.accept(toRawId(border.get()));

			return true;
		}

		return false;
	}

	private static boolean surrounded(int neighbor1, int neighbor2, int neighbor3, int neighbor4, int biome) {
		return neighbor1 == biome && neighbor2 == biome && neighbor3 == biome && neighbor4 == biome;
	}

	private static RegistryKey<Biome> fromRawId(int raw) {
		return BuiltinRegistries.BIOME.getKey(BuiltinRegistries.BIOME.get(raw)).orElseThrow(IllegalStateException::new);
	}

	private static int toRawId(RegistryKey<Biome> key) {
		return BuiltinRegistries.BIOME.getRawId(BuiltinRegistries.BIOME.get(key));
	}
}
