package com.terraformersmc.terraform.boat.impl;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.impl.entity.TerraformBoatEntity;
import com.terraformersmc.terraform.boat.impl.entity.TerraformChestBoatEntity;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class TerraformBoatInitializer implements ModInitializer {
	private static final float DIMENSIONS_WIDTH = 1.375f;
	private static final float DIMENSIONS_HEIGHT = 0.5625f;

	// Hack that prevents the following crash during client startup:
	// Caused by: java.lang.NoClassDefFoundError: Could not initialize class com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry
	private static final Registry<TerraformBoatType> registryInstance = TerraformBoatTypeRegistry.INSTANCE;

	private static final Identifier BOAT_ID = Identifier.of("terraform", "boat");
	public static final EntityType<TerraformBoatEntity> BOAT = EntityType.Builder.<TerraformBoatEntity>create(TerraformBoatEntity::new, SpawnGroup.MISC)
		.dimensions(DIMENSIONS_WIDTH, DIMENSIONS_HEIGHT)
		.build();

	private static final Identifier CHEST_BOAT_ID = Identifier.of("terraform", "chest_boat");
	public static final EntityType<TerraformChestBoatEntity> CHEST_BOAT = EntityType.Builder.<TerraformChestBoatEntity>create(TerraformChestBoatEntity::new, SpawnGroup.MISC)
		.dimensions(DIMENSIONS_WIDTH, DIMENSIONS_HEIGHT)
		.build();

	@Override
	public void onInitialize() {
		TerraformBoatTrackedData.register();
		Registry.register(Registries.ENTITY_TYPE, BOAT_ID, BOAT);
		Registry.register(Registries.ENTITY_TYPE, CHEST_BOAT_ID, CHEST_BOAT);
	}
}
