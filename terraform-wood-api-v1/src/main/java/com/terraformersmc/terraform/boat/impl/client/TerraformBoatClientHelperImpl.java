package com.terraformersmc.terraform.boat.impl.client;

import com.terraformersmc.terraform.boat.impl.data.TerraformBoatData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry.TexturedModelDataProvider;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.RaftEntityModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class TerraformBoatClientHelperImpl {
	private TerraformBoatClientHelperImpl() {
		return;
	}

	private static void registerEntityRenderer(EntityType<? extends AbstractBoatEntity> entityType, EntityModelLayer modelLayer, TexturedModelDataProvider texturedModelDataProvider) {
		EntityModelLayerRegistry.registerModelLayer(modelLayer, texturedModelDataProvider);
		EntityRendererRegistry.register(entityType, context -> new BoatEntityRenderer(context, modelLayer));
	}

	public static void registerModelLayers(Identifier id) {
		TerraformBoatData boatData = TerraformBoatData.get(id);

		if (boatData.boatEntity() != null) {
			registerEntityRenderer(boatData.boatEntity(), boatData.boatModelLayer(),
					BoatEntityModel::getTexturedModelData);
		}
		if (boatData.chestBoatEntity() != null) {
			registerEntityRenderer(boatData.chestBoatEntity(), boatData.chestBoatModelLayer(),
					BoatEntityModel::getChestTexturedModelData);
		}
		if (boatData.raftEntity() != null) {
			registerEntityRenderer(boatData.raftEntity(), boatData.raftModelLayer(),
					RaftEntityModel::getTexturedModelData);
		}
		if (boatData.chestRaftEntity() != null) {
			registerEntityRenderer(boatData.chestRaftEntity(), boatData.chestRaftModelLayer(),
					RaftEntityModel::getChestTexturedModelData);
		}
	}
}
