package com.terraformersmc.terraform.boat.api.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@SuppressWarnings("deprecation")
/**
 * This class provides useful helpers for registering a {@linkplain com.terraformersmc.terraform.boat.api.TerraformBoatType Terraform boat} on the client.
 */
public final class TerraformBoatClientHelper {
	private TerraformBoatClientHelper() {
		return;
	}

	/**
	 * Gets the identifier of a {@linkplain EntityModelLayer model layer} for a boat of a given type.
	 * @param boatId the {@linkplain net.minecraft.util.Identifier identifier} of the {@linkplain com.terraformersmc.terraform.boat.api.TerraformBoatType boat}
	 */
	private static Identifier getLayerId(Identifier boatId) {
		return new Identifier(boatId.getNamespace(), "boat/" + boatId.getPath());
	}

	/**
	 * Creates a {@linkplain EntityModelLayer model layer} for a boat of a given type.
	 * @param boatId the {@linkplain net.minecraft.util.Identifier identifier} of the {@linkplain com.terraformersmc.terraform.boat.api.TerraformBoatType boat}
	 * 
	 * <pre>{@code
	 *     EntityModelLayer layer = TerraformBoatClientHelper.getLayer(new Identifier("examplemod", "mahogany"));
	 * }</pre>
	 */
	public static EntityModelLayer getLayer(Identifier boatId) {
		return new EntityModelLayer(getLayerId(boatId), "main");
	}

	/**
	 * Registers a {@linkplain EntityModelLayer model layer} for a boat of a given type.
	 * @param boatId the {@linkplain net.minecraft.util.Identifier identifier} of the {@linkplain com.terraformersmc.terraform.boat.api.TerraformBoatType boat}
	 * 
	 * <pre>{@code
	 *     TerraformBoatClientHelper.registerModelLayer(new Identifier("examplemod", "mahogany"));
	 * }</pre>
	 */
	public static void registerModelLayer(Identifier boatId) {
		EntityModelLayerRegistry.registerModelLayer(getLayer(boatId), BoatEntityModel::getTexturedModelData);
	}
}
