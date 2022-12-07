package com.terraformersmc.terraform.boat.impl;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.impl.entity.TerraformBoatEntity;
import com.terraformersmc.terraform.boat.impl.entity.TerraformChestBoatEntity;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class TerraformBoatInitializer implements ModInitializer {
	private static final EntityDimensions DIMENSIONS = EntityDimensions.fixed(1.375f, 0.5625f);

	// Hack that prevents the following crash during client startup:
	// Caused by: java.lang.NoClassDefFoundError: Could not initialize class com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry
	private static final Registry<TerraformBoatType> registryInstance = TerraformBoatTypeRegistry.INSTANCE;

	private static final Identifier BOAT_ID = new Identifier("terraform", "boat");
	public static final EntityType<TerraformBoatEntity> BOAT = FabricEntityTypeBuilder.<TerraformBoatEntity>create(SpawnGroup.MISC, TerraformBoatEntity::new)
		.dimensions(DIMENSIONS)
		.build();

	private static final Identifier CHEST_BOAT_ID = new Identifier("terraform", "chest_boat");
	public static final EntityType<TerraformChestBoatEntity> CHEST_BOAT = FabricEntityTypeBuilder.<TerraformChestBoatEntity>create(SpawnGroup.MISC, TerraformChestBoatEntity::new)
		.dimensions(DIMENSIONS)
		.build();

	@Override
	public void onInitialize() {
		TerraformBoatTrackedData.register();
		Registry.register(Registries.ENTITY_TYPE, BOAT_ID, BOAT);
		Registry.register(Registries.ENTITY_TYPE, CHEST_BOAT_ID, CHEST_BOAT);
	}
}
