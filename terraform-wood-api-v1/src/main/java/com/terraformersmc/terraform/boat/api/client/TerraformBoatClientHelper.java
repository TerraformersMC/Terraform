package com.terraformersmc.terraform.boat.api.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
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
	 * @param chest whether the boat contains a chest
	 */
	private static Identifier getLayerId(Identifier boatId, boolean chest) {
		String prefix = chest ? "chest_boat/" : "boat/";
		return new Identifier(boatId.getNamespace(), prefix + boatId.getPath());
	}

	/**
	 * Creates a {@linkplain EntityModelLayer model layer} for a boat of a given type.
	 * @param boatId the {@linkplain net.minecraft.util.Identifier identifier} of the {@linkplain com.terraformersmc.terraform.boat.api.TerraformBoatType boat}
	 * @param chest whether the boat contains a chest
	 * 
	 * <pre>{@code
	 *     EntityModelLayer layer = TerraformBoatClientHelper.getLayer(new Identifier("examplemod", "mahogany"), false);
	 * }</pre>
	 */
	public static EntityModelLayer getLayer(Identifier boatId, boolean chest) {
		return new EntityModelLayer(getLayerId(boatId, chest), "main");
	}

	/**
	 * Registers a {@linkplain EntityModelLayer model layer} for a boat of a given type.
	 * @param boatId the {@linkplain net.minecraft.util.Identifier identifier} of the {@linkplain com.terraformersmc.terraform.boat.api.TerraformBoatType boat}
	 * 
	 * <pre>{@code
	 *     TerraformBoatClientHelper.registerModelLayer(new Identifier("examplemod", "mahogany"), false);
	 * }</pre>
	 */
	private static void registerModelLayer(Identifier boatId, boolean chest) {
		EntityModelLayerRegistry.registerModelLayer(getLayer(boatId, chest), () -> BoatEntityModel.getTexturedModelData(chest));
	}

	/**
	 * Registers {@linkplain EntityModelLayer model layers} for a given boat type.
	 * @param boatId the {@linkplain net.minecraft.util.Identifier identifier} of the {@linkplain com.terraformersmc.terraform.boat.api.TerraformBoatType boat type}
	 * 
	 * <pre>{@code
	 *     TerraformBoatClientHelper.registerModelLayers(new Identifier("examplemod", "mahogany"));
	 * }</pre>
	 */
	public static void registerModelLayers(Identifier boatId) {
		registerModelLayer(boatId, false);
		registerModelLayer(boatId, true);
	}
}
