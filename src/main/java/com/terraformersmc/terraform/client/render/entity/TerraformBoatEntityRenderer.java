package com.terraformersmc.terraform.client.render.entity;

import com.terraformersmc.terraform.entity.TerraformBoatEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class TerraformBoatEntityRenderer extends BoatEntityRenderer {
	public TerraformBoatEntityRenderer(EntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	protected Identifier method_3891(BoatEntity boat) {
		if(boat instanceof TerraformBoatEntity) {
			return ((TerraformBoatEntity) boat).getBoatSkin();
		} else {
			return super.method_3891(boat);
		}
	}
}
