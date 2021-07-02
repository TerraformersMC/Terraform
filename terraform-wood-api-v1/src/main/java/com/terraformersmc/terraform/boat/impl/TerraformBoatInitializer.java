package com.terraformersmc.terraform.boat.impl;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class TerraformBoatInitializer implements ModInitializer {
	private static final Identifier BOAT_ID = new Identifier("terraform", "boat");
	public static final EntityType<TerraformBoatEntity> BOAT = FabricEntityTypeBuilder.<TerraformBoatEntity>create(SpawnGroup.MISC, TerraformBoatEntity::new)
		.dimensions(EntityDimensions.fixed(1.375f, 0.5625f))
		.build();

	@Override
	public void onInitialize() {
		TerraformBoatTrackedData.register();
		Registry.register(Registry.ENTITY_TYPE, BOAT_ID, BOAT);
	}
}
