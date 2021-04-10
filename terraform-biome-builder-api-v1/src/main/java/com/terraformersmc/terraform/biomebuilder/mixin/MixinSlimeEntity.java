package com.terraformersmc.terraform.biomebuilder.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;

import com.terraformersmc.terraform.biomebuilder.TerraformSlimeSpawnBiomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(SlimeEntity.class)
public abstract class MixinSlimeEntity extends MobEntity implements Monster {
	public MixinSlimeEntity(EntityType<? extends MobEntity> type, World world) {
		super(type, world);
	}

	@Inject(method = "canSpawn(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)Z", at = @At(value = "HEAD"), cancellable = true)
	private static void canSpawnInjection(EntityType<SlimeEntity> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> info) {
		if (world.getDifficulty() == Difficulty.PEACEFUL) {
			return; // Delegate back to vanilla
		}

		RegistryKey<Biome> biomeKey = world.getBiomeKey(pos).orElse(null);
		boolean hasSurfaceSlimeSpawns = TerraformSlimeSpawnBiomes.getSlimeSpawnBiomes().contains(biomeKey);

		// Handle spawning for biomes registered as slime-spawnable
		if (hasSurfaceSlimeSpawns && pos.getY() > 50 && pos.getY() < 70 && random.nextFloat() < 0.5F && random.nextFloat() < world.getMoonSize() && world.getLightLevel(pos) <= random.nextInt(8)) {
			info.setReturnValue(canMobSpawn(type, world, reason, pos, random));
		}
	}
}
