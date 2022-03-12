package com.terraformersmc.terraform.biometag.impl.mixin;

import com.terraformersmc.terraform.biometag.api.TerraformBiomeTags;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

@Mixin(SlimeEntity.class)
public class MixinSlimeEntity {
	@Redirect(method = "canSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/registry/RegistryEntry;matchesKey(Lnet/minecraft/util/registry/RegistryKey;)Z"))
	private static boolean canSpawnInjection(RegistryEntry<Biome> entry, RegistryKey<Biome> key) {
		if (key == BiomeKeys.SWAMP) {
			return entry.isIn(TerraformBiomeTags.SURFACE_SLIME_SPAWNS);
		}
		return entry.matchesKey(key);
	}
}
