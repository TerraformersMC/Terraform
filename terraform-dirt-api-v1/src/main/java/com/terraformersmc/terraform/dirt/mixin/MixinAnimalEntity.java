package com.terraformersmc.terraform.dirt.mixin;

import java.util.Random;

import com.terraformersmc.terraform.dirt.TerraformDirtBlockTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

@Mixin(AnimalEntity.class)
public class MixinAnimalEntity {
	@Inject(method = "isValidNaturalSpawn(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)Z", at = @At("HEAD"), cancellable = true)
	private static void terraform$spawnOnCustomGrass(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
		if(TerraformDirtBlockTags.GRASS_BLOCKS.contains(world.getBlockState(pos.down()).getBlock()) && world.getBaseLightLevel(pos, 0) > 8) {
			cir.setReturnValue(true);
		}
	}
}
