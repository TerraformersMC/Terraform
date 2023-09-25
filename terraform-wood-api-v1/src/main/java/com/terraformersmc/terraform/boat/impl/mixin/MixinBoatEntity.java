package com.terraformersmc.terraform.boat.impl.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import com.terraformersmc.terraform.boat.impl.entity.TerraformBoatHolder;

import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemConvertible;

@Mixin(BoatEntity.class)
public class MixinBoatEntity {
	@ModifyArg(method = "fall(DZLnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)V", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/BoatEntity;dropItem(Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/entity/ItemEntity;", ordinal = 0))
	private ItemConvertible terraformWood$replacePlanksDropItem(ItemConvertible original) {
		if (this instanceof TerraformBoatHolder terraformBoatHolder) {
			return terraformBoatHolder.getTerraformBoat().getPlanks();
		}

		return original;
	}
}
