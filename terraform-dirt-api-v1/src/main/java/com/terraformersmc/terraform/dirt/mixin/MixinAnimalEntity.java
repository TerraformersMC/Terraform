package com.terraformersmc.terraform.dirt.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.terraformersmc.terraform.dirt.TerraformDirtBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnimalEntity.class)
public class MixinAnimalEntity {
	@WrapOperation(
			method = "isValidNaturalSpawn",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z")
	)
	@SuppressWarnings("unused")
	private static boolean terraformDirt$spawnOnCustomGrass(BlockState instance, TagKey<Block> grassTag, Operation<Boolean> operation) {
		if (instance.isIn(TerraformDirtBlockTags.GRASS_BLOCKS)) {
			return true;
		}

		return operation.call(instance, grassTag);
	}
}
