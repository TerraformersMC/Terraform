package com.terraformersmc.terraform.boat.impl;

import com.terraformersmc.terraform.boat.impl.data.TerraformBoatDataImpl;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry.TexturedModelDataProvider;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.model.object.boat.RaftModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.RaftRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public final class TerraformBoatClientHelperImpl {
	@SuppressWarnings("UnnecessaryReturnStatement")
	private TerraformBoatClientHelperImpl() {
		return;
	}

	private static <T extends Entity> void registerEntityRenderer(EntityType<? extends T> entityType, ModelLayerLocation modelLayer, TexturedModelDataProvider texturedModelDataProvider, EntityRendererProvider<T> entityRendererFactory) {
		EntityModelLayerRegistry.registerModelLayer(modelLayer, texturedModelDataProvider);
		EntityRenderers.register(entityType, entityRendererFactory);
	}

	public static void registerModelLayers(Identifier id) {
		TerraformBoatDataImpl boatData = TerraformBoatDataImpl.get(id);

		if (boatData.boatEntityType() != null) {
			ModelLayerLocation modelLayers = new ModelLayerLocation(boatData.boatModelLayerId(), "main");
			registerEntityRenderer(boatData.boatEntityType(), modelLayers,
					BoatModel::createBoatModel,
					context -> new BoatRenderer(context, modelLayers));
		}
		if (boatData.chestBoatEntityType() != null) {
			ModelLayerLocation modelLayers = new ModelLayerLocation(boatData.chestBoatModelLayerId(), "main");
			registerEntityRenderer(boatData.chestBoatEntityType(), modelLayers,
					BoatModel::createChestBoatModel,
					context -> new BoatRenderer(context, modelLayers));
		}
		if (boatData.raftEntityType() != null) {
			ModelLayerLocation modelLayers = new ModelLayerLocation(boatData.raftModelLayerId(), "main");
			registerEntityRenderer(boatData.raftEntityType(), modelLayers,
					RaftModel::createRaftModel,
					context -> new RaftRenderer(context, modelLayers));
		}
		if (boatData.chestRaftEntityType() != null) {
			ModelLayerLocation modelLayers = new ModelLayerLocation(boatData.chestRaftModelLayerId(), "main");
			registerEntityRenderer(boatData.chestRaftEntityType(), modelLayers,
					RaftModel::createChestRaftModel,
					context -> new RaftRenderer(context, modelLayers));
		}
	}
}
