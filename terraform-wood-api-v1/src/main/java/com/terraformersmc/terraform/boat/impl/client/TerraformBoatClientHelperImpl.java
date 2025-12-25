package com.terraformersmc.terraform.boat.impl.client;

import com.terraformersmc.terraform.boat.impl.data.TerraformBoatDataImpl;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public final class TerraformBoatClientHelperImpl {
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
			registerEntityRenderer(boatData.boatEntityType(), boatData.boatModelLayer(),
					BoatModel::createBoatModel,
					context -> new BoatRenderer(context, boatData.boatModelLayer()));
		}
		if (boatData.chestBoatEntityType() != null) {
			registerEntityRenderer(boatData.chestBoatEntityType(), boatData.chestBoatModelLayer(),
					BoatModel::createChestBoatModel,
					context -> new BoatRenderer(context, boatData.chestBoatModelLayer()));
		}
		if (boatData.raftEntityType() != null) {
			registerEntityRenderer(boatData.raftEntityType(), boatData.raftModelLayer(),
					RaftModel::createRaftModel,
					context -> new RaftRenderer(context, boatData.raftModelLayer()));
		}
		if (boatData.chestRaftEntityType() != null) {
			registerEntityRenderer(boatData.chestRaftEntityType(), boatData.chestRaftModelLayer(),
					RaftModel::createChestRaftModel,
					context -> new RaftRenderer(context, boatData.chestRaftModelLayer()));
		}
	}
}
