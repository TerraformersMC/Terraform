package com.terraformersmc.terraform.biomebuilder.mixin;

import com.terraformersmc.terraform.biomebuilder.PrivateSlimeSpawnData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.world.biome.Biome;

@Mixin(Biome.class)
public class MixinBiome implements PrivateSlimeSpawnData.BiomeExtension {
	@Unique
	private boolean surfaceSlimeSpawns;

	@Override
	public void setSurfaceSlimeSpawns() {
		surfaceSlimeSpawns = true;
	}

	@Override
	public boolean hasSurfaceSlimeSpawns() {
		return surfaceSlimeSpawns;
	}
}
