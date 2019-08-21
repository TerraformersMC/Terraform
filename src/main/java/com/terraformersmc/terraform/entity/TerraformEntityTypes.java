package com.terraformersmc.terraform.entity;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TerraformEntityTypes implements ModInitializer {
	/*public static final EntityType<TerraformBoatEntity> TERRAFORM_BOAT =
		EntityType.Builder.create(TerraformBoatEntity::create, EntityCategory.MISC)
		.setDimensions(1.375F, 0.5625F)
		.build("terraform:boat");*/

	@Override
	public void onInitialize() {
		// Registry.register(Registry.ENTITY_TYPE, new Identifier("terraform", "boat"), TERRAFORM_BOAT);
	}
}
