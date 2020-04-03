package com.terraformersmc.terraform.mixin;

import java.util.Random;

import com.terraformersmc.terraform.util.TerraformBiomeSets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

@Mixin(SlimeEntity.class)
public abstract class MixinSlimeEntity extends MobEntity implements Monster {
	public MixinSlimeEntity(EntityType<? extends MobEntity> type, World world) {
		super(type, world);
	}

	@Inject(method = "canSpawn", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/IWorld;getBiome(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/world/biome/Biome;"), cancellable = true)
	private static void canSpawnInjection(EntityType<SlimeEntity> type, IWorld world, SpawnType spawnType, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> info) {
		Biome biome = world.getBiome(pos);
		if (TerraformBiomeSets.getSlimeSpawnBiomes().contains(biome) && pos.getY() > 50 && pos.getY() < 70 && random.nextFloat() < 0.5F && random.nextFloat() < world.getMoonSize() && world.getLightLevel(pos) <= random.nextInt(8)) {
			info.setReturnValue(canMobSpawn(type, world, spawnType, pos, random));
		}
	}
}
