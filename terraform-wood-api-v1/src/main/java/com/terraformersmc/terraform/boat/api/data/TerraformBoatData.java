package com.terraformersmc.terraform.boat.api.data;

import com.terraformersmc.terraform.boat.impl.data.TerraformBoatDataImpl;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.entity.vehicle.ChestRaftEntity;
import net.minecraft.entity.vehicle.RaftEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.Optional;

/**
 * Read-only access interface for boat data records used by the boat API to register boats.
 * Provides access to identifiers, entity types, and model layer data.
 */
@SuppressWarnings("unused")
public interface TerraformBoatData {
	/**
	 * Get TerraformBoatData for the requested boat family ID.
	 * This can be used to fetch various IDs and objects for the boats in the family.
	 *
	 * @param id ID of the requested boat family
	 * @return TerraformBoatData for the requested boat family
	 */
	static TerraformBoatDataImpl get(Identifier id) {
		return TerraformBoatDataImpl.get(id);
	}

	/**
	 * Get TerraformBoatData for the requested boat family ID.
	 * This can be used to fetch various IDs and objects for the boats in the family.
	 *
	 * @param id ID of the requested boat family
	 * @return TerraformBoatData for the requested boat family
	 */
	static Optional<TerraformBoatData> getOptional(Identifier id) {
		return TerraformBoatDataImpl.getOptional(id).map(data -> data);
	}


	/**
	 * Get the boat item's ID.
	 *
	 * @return Identifier of the boat item
	 */
	Identifier boatId();

	/**
	 * Get the chest boat item's ID.
	 *
	 * @return Identifier of the chest boat item
	 */
	Identifier chestBoatId();

	/**
	 * Get the boat item's registry key.
	 *
	 * @return RegistryKey of the boat item
	 */
	RegistryKey<Item> boatKey();

	/**
	 * Get the chest boat item's registry key.
	 *
	 * @return RegistryKey of the chest boat item
	 */
	RegistryKey<Item> chestBoatKey();

	/**
	 * Get the boat's EntityType ID.
	 *
	 * @return Identifier of the boat's entity type
	 */
	Identifier boatEntityTypeId();

	/**
	 * Get the chest boat's EntityType ID.
	 *
	 * @return Identifier of the chest boat's entity type
	 */
	Identifier chestBoatEntityTypeId();

	/**
	 * Get the boat entity type's registry key.
	 *
	 * @return RegistryKey of the boat entity type
	 */
	RegistryKey<EntityType<?>> boatEntityTypeKey();

	/**
	 * Get the chest boat entity type's registry key.
	 *
	 * @return RegistryKey of the chest boat entity type
	 */
	RegistryKey<EntityType<?>> chestBoatEntityTypeKey();

	/**
	 * Get the boat's {@link EntityType<BoatEntity>} object.
	 *
	 * @return EntityType of the boat.
	 */
	EntityType<BoatEntity> boatEntityType();

	/**
	 * Get the chest boat's {@link EntityType<ChestBoatEntity>} object.
	 *
	 * @return EntityType of the chest boat.
	 */
	EntityType<ChestBoatEntity> chestBoatEntityType();

	/**
	 * Get the boat's model layers.
	 *
	 * @return EntityModelLayer of the boat
	 */
	EntityModelLayer boatModelLayer();

	/**
	 * Get the chest boat's model layers.
	 *
	 * @return EntityModelLayer of the chest boat
	 */
	EntityModelLayer chestBoatModelLayer();


	/**
	 * Get the raft item's ID.
	 *
	 * @return Identifier of the raft item
	 */
	Identifier raftId();

	/**
	 * Get the chest raft item's ID.
	 *
	 * @return Identifier of the chest raft item
	 */
	Identifier chestRaftId();

	/**
	 * Get the raft item's registry key.
	 *
	 * @return RegistryKey of the raft item
	 */
	RegistryKey<Item> raftKey();

	/**
	 * Get the chest raft item's registry key.
	 *
	 * @return RegistryKey of the chest raft item
	 */
	RegistryKey<Item> chestRaftKey();

	/**
	 * Get the raft's EntityType ID.
	 *
	 * @return Identifier of the raft's entity type
	 */
	Identifier raftEntityTypeId();

	/**
	 * Get the chest raft's EntityType ID.
	 *
	 * @return Identifier of the chest raft's entity type
	 */
	Identifier chestRaftEntityTypeId();

	/**
	 * Get the raft entity type's registry key.
	 *
	 * @return RegistryKey of the raft entity type
	 */
	RegistryKey<EntityType<?>> raftEntityTypeKey();

	/**
	 * Get the chest raft entity type's registry key.
	 *
	 * @return RegistryKey of the chest raft entity type
	 */
	RegistryKey<EntityType<?>> chestRaftEntityTypeKey();

	/**
	 * Get the raft's {@link EntityType<RaftEntity>} object.
	 *
	 * @return EntityType of the raft.
	 */
	EntityType<RaftEntity> raftEntityType();

	/**
	 * Get the chest raft's {@link EntityType<ChestRaftEntity>} object.
	 *
	 * @return EntityType of the chest raft.
	 */
	EntityType<ChestRaftEntity> chestRaftEntityType();

	/**
	 * Get the raft's model layers.
	 *
	 * @return EntityModelLayer of the raft
	 */
	EntityModelLayer raftModelLayer();

	/**
	 * Get the chest raft's model layers.
	 *
	 * @return EntityModelLayer of the chest raft
	 */
	EntityModelLayer chestRaftModelLayer();
}
