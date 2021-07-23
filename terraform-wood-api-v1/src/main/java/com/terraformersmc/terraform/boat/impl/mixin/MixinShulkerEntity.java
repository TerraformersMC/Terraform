package com.terraformersmc.terraform.boat.impl.mixin;

import com.terraformersmc.terraform.boat.impl.TerraformBoatInitializer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ShulkerEntity;

@Mixin(ShulkerEntity.class)
public class MixinShulkerEntity {
	@Redirect(method = "getHeightOffset", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getType()Lnet/minecraft/entity/EntityType;"))
	private EntityType<?> fixTerraformBoatHeightOffset(Entity entity) {
		if (entity.getType() == TerraformBoatInitializer.BOAT) {
			return EntityType.BOAT;
		}
		return entity.getType();
	}
}
