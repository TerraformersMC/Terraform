package com.terraformersmc.terraform.boat.impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.terraformersmc.terraform.boat.impl.TerraformBoatInitializer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.predicate.entity.EntityPredicate;

@Mixin(EntityPredicate.class)
public class MixinEntityPredicate {
	@WrapOperation(
			method = "test(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/entity/Entity;)Z",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getType()Lnet/minecraft/entity/EntityType;")
	)
	private EntityType<?> terraformWood$useVanillaBoatTypeForPredicates(Entity entity, Operation<EntityType<?>> operation) {
		EntityType<?> type = operation.call(entity);

		if (type == TerraformBoatInitializer.BOAT) {
			return EntityType.BOAT;
		} else if (type == TerraformBoatInitializer.CHEST_BOAT) {
			return EntityType.CHEST_BOAT;
		}

		return type;
	}
}
