package com.terraformersmc.terraform.biomeremapper.mixin;

import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import com.terraformersmc.terraform.biomeremapper.impl.BiomeRemappings;
import com.terraformersmc.terraform.biomeremapper.impl.BiomeRemappings.RemappingRecord;
import net.minecraft.util.filefix.FileFixerUpper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Hashtable;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.NamespacedTypeRenameFix;
import net.minecraft.util.datafix.fixes.References;

import static com.terraformersmc.terraform.biomeremapper.impl.BiomeRemappings.BIOME_REMAPPING_REGISTRY;

@Mixin(DataFixers.class)
public class MixinDataFixers {
	@Shadow
	@Final
	private static BiFunction<Integer, Schema, Schema> SAME_NAMESPACED;

	@Shadow
	@SuppressWarnings("ConstantConditions")
	private static UnaryOperator<String> createRenamer(Map<String, String> replacements) { return null; }

	@Inject(method = "addFixers", at = @At("TAIL"))
	private static void terraformBiomeRemapper$injectDataFixers(final DataFixerBuilder fixerUpper, final FileFixerUpper.Builder fileFixerUpper, CallbackInfo ci) {
		final Hashtable<Integer, Schema> SCHEMA_CACHE = new Hashtable<>(2);

		// This collects all the requested remappings into BIOME_REMAPPING_REGISTRY.
		BiomeRemappings.invokeEndpoints();

		// Then we iterate over them and inject them into the DFU.
		for (String key : BIOME_REMAPPING_REGISTRY.keySet().stream().sorted().toList()) {
			RemappingRecord remappingRecord = BIOME_REMAPPING_REGISTRY.get(key);
			// We use a single schema for each targeted Minecraft data version.
			Schema schema = SCHEMA_CACHE.computeIfAbsent(
					remappingRecord.dataVersion(),
					dataVersion -> fixerUpper.addSchema(dataVersion, SAME_NAMESPACED)
			);
			// Associate the requested schema with a freshly built fix for each remapping.
			fixerUpper.addFixer(new NamespacedTypeRenameFix(
					schema,
					"Terraform biome remapper fix for " + remappingRecord.modId() + " at data version " + remappingRecord.dataVersion(),
					References.BIOME,
					createRenamer(remappingRecord.remapping())
			));
		}
	}
}
