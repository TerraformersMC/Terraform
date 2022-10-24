package com.terraformersmc.terraform.boat.api.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry.TexturedModelDataProvider;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.ChestBoatEntityModel;
import net.minecraft.client.render.entity.model.ChestRaftEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.RaftEntityModel;
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
	 * @param raft whether the boat is a raft
	 * @param chest whether the boat contains a chest
	 */
	private static Identifier getLayerId(Identifier boatId, boolean raft, boolean chest) {
		String prefix = raft ? (chest ? "chest_raft/" : "raft/") : (chest ? "chest_boat/" : "boat/");
		return new Identifier(boatId.getNamespace(), prefix + boatId.getPath());
	}

	/**
	 * Creates a {@linkplain EntityModelLayer model layer} for a boat of a given type.
	 * @param boatId the {@linkplain net.minecraft.util.Identifier identifier} of the {@linkplain com.terraformersmc.terraform.boat.api.TerraformBoatType boat}
	 * @param raft whether the boat is a raft
	 * @param chest whether the boat contains a chest
	 * 
	 * <pre>{@code
	 *     EntityModelLayer layer = TerraformBoatClientHelper.getLayer(new Identifier("examplemod", "mahogany"), false, false);
	 * }</pre>
	 */
	public static EntityModelLayer getLayer(Identifier boatId, boolean raft, boolean chest) {
		return new EntityModelLayer(getLayerId(boatId, raft, chest), "main");
	}

	private static TexturedModelDataProvider getTexturedModelDataProvider(boolean raft, boolean chest) {
		if (raft) {
			return chest ? ChestRaftEntityModel::method_45709 : RaftEntityModel::getTexturedModelData;
		} else {
			return chest ? ChestBoatEntityModel::method_45708 : BoatEntityModel::getTexturedModelData;
		}
	}

	/**
	 * Registers a {@linkplain EntityModelLayer model layer} for a boat of a given type.
	 * @param boatId the {@linkplain net.minecraft.util.Identifier identifier} of the {@linkplain com.terraformersmc.terraform.boat.api.TerraformBoatType boat}
	 * @param raft whether the boat is a raft
	 * @param chest whether the boat contains a chest
	 * 
	 * <pre>{@code
	 *     TerraformBoatClientHelper.registerModelLayer(new Identifier("examplemod", "mahogany"), false, false);
	 * }</pre>
	 */
	private static void registerModelLayer(Identifier boatId, boolean raft, boolean chest) {
		EntityModelLayerRegistry.registerModelLayer(getLayer(boatId, raft, chest), getTexturedModelDataProvider(raft, chest));
	}

	/**
	 * Registers {@linkplain EntityModelLayer model layers} for a given boat type.
	 * @param boatId the {@linkplain net.minecraft.util.Identifier identifier} of the {@linkplain com.terraformersmc.terraform.boat.api.TerraformBoatType boat type}
	 * @param raft whether the boat is a raft
	 * 
	 * <pre>{@code
	 *     TerraformBoatClientHelper.registerModelLayers(new Identifier("examplemod", "mahogany"), false);
	 * }</pre>
	 */
	public static void registerModelLayers(Identifier boatId, boolean raft) {
		registerModelLayer(boatId, raft, false);
		registerModelLayer(boatId, raft, true);
	}
}
