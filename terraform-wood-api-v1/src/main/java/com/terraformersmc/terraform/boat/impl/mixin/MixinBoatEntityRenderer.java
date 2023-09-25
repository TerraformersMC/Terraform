package com.terraformersmc.terraform.boat.impl.mixin;

import java.util.Map;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.util.Pair;
import com.terraformersmc.terraform.boat.impl.client.TerraformBoatEntityRenderer;
import com.terraformersmc.terraform.boat.impl.entity.TerraformBoatHolder;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;

@Mixin(BoatEntityRenderer.class)
@Environment(EnvType.CLIENT)
public class MixinBoatEntityRenderer {
	@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
	@SuppressWarnings("unused")
	private Object terraformWood$getBoatTextureAndModel(Map<BoatEntity.Type, Pair<Identifier, BoatEntityModel>> instance, Object type, Operation<Object> original, BoatEntity entity) {
		//noinspection ConstantConditions
		if (entity instanceof TerraformBoatHolder terraformEntity &&
				(Object) this instanceof TerraformBoatEntityRenderer terraformRenderer) {
			return terraformRenderer.getTextureAndModel(terraformEntity);
		}

		return original.call(instance, type);
	}
}
