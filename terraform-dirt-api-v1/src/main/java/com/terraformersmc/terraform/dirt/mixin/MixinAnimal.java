package com.terraformersmc.terraform.dirt.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.terraformersmc.terraform.dirt.api.TerraformDirtBlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Animal.class)
public class MixinAnimal {
	@WrapOperation(
			method = "checkAnimalSpawnRules",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/TagKey;)Z")
	)
	@SuppressWarnings("unused")
	private static boolean terraformDirt$spawnOnCustomGrass(BlockState instance, TagKey<Block> grassTag, Operation<Boolean> operation) {
		if (instance.is(TerraformDirtBlockTags.GRASS_BLOCKS)) {
			return true;
		}

		return operation.call(instance, grassTag);
	}
}
