package com.terraformersmc.terraform.biomebuilder.mixin;

import com.terraformersmc.terraform.biomebuilder.PrivateSlimeSpawnData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.biome.Biome;

@Mixin(Biome.Builder.class)
public class MixinBiomeBuilder implements PrivateSlimeSpawnData.BuilderExtension {
	@Unique
	private boolean surfaceSlimeSpawns;

	@Inject(method = "build()Lnet/minecraft/world/biome/Biome;", at = @At("RETURN"))
	private void terraform$setSlimeSpawnBiome(CallbackInfoReturnable<Biome> cir) {
		if (surfaceSlimeSpawns) {
			((PrivateSlimeSpawnData.BiomeExtension) (Object) cir.getReturnValue()).setSurfaceSlimeSpawns();
		}
	}

	@Override
	public void surfaceSlimeSpawns() {
		surfaceSlimeSpawns = true;
	}
}
