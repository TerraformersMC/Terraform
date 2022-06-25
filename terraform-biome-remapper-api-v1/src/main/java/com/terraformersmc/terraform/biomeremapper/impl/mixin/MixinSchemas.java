package com.terraformersmc.terraform.biomeremapper.impl.mixin;

import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import com.terraformersmc.terraform.biomeremapper.impl.BiomeRemappings;
import com.terraformersmc.terraform.biomeremapper.impl.BiomeRemappings.RemappingRecord;
import net.minecraft.datafixer.Schemas;
import net.minecraft.datafixer.fix.BiomeRenameFix;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Hashtable;
import java.util.function.BiFunction;

import static com.terraformersmc.terraform.biomeremapper.impl.BiomeRemappings.BIOME_REMAPPING_REGISTRY;

@Mixin(Schemas.class)
public class MixinSchemas {
	@Shadow
	@Final
	private static BiFunction<Integer, Schema, Schema> EMPTY_IDENTIFIER_NORMALIZE;

	@Inject(method = "build", at = @At("TAIL"))
	private static void terraformBiomeRemapper$injectDataFixers(DataFixerBuilder builder, CallbackInfo ci) {
		final Hashtable<Integer, Schema> SCHEMA_CACHE = new Hashtable<>(2);

		// This collects all the requested remappings into BIOME_REMAPPING_REGISTRY.
		BiomeRemappings.invokeEndpoints();

		// Then we iterate over them and inject them into the DFU.
		for (String key : BIOME_REMAPPING_REGISTRY.keySet().stream().sorted().toList()) {
			RemappingRecord remappingRecord = BIOME_REMAPPING_REGISTRY.get(key);
			// We use a single schema for each targeted Minecraft data version.
			Schema schema = SCHEMA_CACHE.computeIfAbsent(
					remappingRecord.dataVersion(),
					dataVersion -> builder.addSchema(dataVersion, EMPTY_IDENTIFIER_NORMALIZE)
			);
			// Associate the requested schema with a freshly built fix for each remapping.
			builder.addFixer(new BiomeRenameFix(
					schema, false,
					"Terraform biome remapper fix for " + remappingRecord.modId() + " at data version " + remappingRecord.dataVersion(),
					remappingRecord.remapping()
			));
		}
	}
}
