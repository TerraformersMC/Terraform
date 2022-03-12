package com.terraformersmc.terraform.biometag.impl.mixin;

import com.terraformersmc.terraform.biometag.api.TerraformBiomeTags;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.item.BoneMealItem;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

@Mixin(BoneMealItem.class)
public class MixinBoneMealItem {
	@Redirect(method = "useOnGround", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/registry/RegistryEntry;matchesKey(Lnet/minecraft/util/registry/RegistryKey;)Z"))
	private static boolean useOnGroundInjection(RegistryEntry<Biome> entry, RegistryKey<Biome> key) {
		if (key == BiomeKeys.WARM_OCEAN) {
			return entry.isIn(TerraformBiomeTags.WARM_UNDERWATER_FERTILIZATION);
		}
		return entry.matchesKey(key);
	}
}
