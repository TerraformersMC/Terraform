package com.terraformersmc.terraform.boat.impl.client;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.boat.impl.entity.TerraformBoatHolder;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class TerraformBoatEntityRenderer extends BoatEntityRenderer {
	private final Map<TerraformBoatType, Pair<Identifier, BoatEntityModel>> texturesAndModels;

	public TerraformBoatEntityRenderer(EntityRendererFactory.Context context, boolean chest) {
		super(context, chest);

		String prefix = chest ? "chest_boat/" : "boat/";
		this.texturesAndModels = TerraformBoatTypeRegistry.INSTANCE.getEntrySet().stream().collect(ImmutableMap.toImmutableMap(entry -> {
			return entry.getValue();
		}, entry -> {
			Identifier id = entry.getKey().getValue();
			Identifier textureId = new Identifier(id.getNamespace(), "textures/entity/" + prefix + id.getPath() + ".png");

			EntityModelLayer layer = TerraformBoatClientHelper.getLayer(id, chest);
			BoatEntityModel model = new BoatEntityModel(context.getPart(layer), chest);

			return new Pair<>(textureId, model);
		}));
	}

	@Override
	public Identifier getTexture(BoatEntity entity) {
		if (entity instanceof TerraformBoatHolder) {
			TerraformBoatType boat = ((TerraformBoatHolder) entity).getTerraformBoat();
			return this.texturesAndModels.get(boat).getFirst();
		}
		return super.getTexture(entity);
	}

	public Pair<Identifier, BoatEntityModel> getTextureAndModel(TerraformBoatHolder holder) {
		return this.texturesAndModels.get(holder.getTerraformBoat());
	}
}
