package com.terraformersmc.terraform.boat.api.data;

import com.terraformersmc.terraform.boat.impl.data.TerraformBoatDataImpl;
import java.util.Optional;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.entity.vehicle.boat.ChestRaft;
import net.minecraft.world.entity.vehicle.boat.Raft;
import net.minecraft.world.item.Item;

/**
 * Read-only access interface for boat data records used by the boat API to register boats.
 * Provides access to identifiers and entity types.
 */
@SuppressWarnings("unused")
public interface TerraformBoatData {
	/**
	 * Get TerraformBoatData for the requested boat family ID.
	 * This can be used to fetch various IDs and objects for the boats in the family.
	 * <p/>
	 * This method throws an exception if the id has not been registered.
	 *
	 * @param id ID of the requested boat family
	 * @return TerraformBoatData for the requested boat family
	 */
	static TerraformBoatData get(Identifier id) {
		return TerraformBoatDataImpl.get(id);
	}

	/**
	 * Get TerraformBoatData for the requested boat family ID.
	 * This can be used to fetch various IDs and objects for the boats in the family.
	 * <p/>
	 * This method allows the consumer to safely operate on boat data only if it is present.
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
	ResourceKey<Item> boatKey();

	/**
	 * Get the chest boat item's registry key.
	 *
	 * @return RegistryKey of the chest boat item
	 */
	ResourceKey<Item> chestBoatKey();

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
	ResourceKey<EntityType<?>> boatEntityTypeKey();

	/**
	 * Get the chest boat entity type's registry key.
	 *
	 * @return RegistryKey of the chest boat entity type
	 */
	ResourceKey<EntityType<?>> chestBoatEntityTypeKey();

	/**
	 * Get the boat's {@link EntityType<Boat>} object.
	 *
	 * @return EntityType of the boat.
	 */
	EntityType<Boat> boatEntityType();

	/**
	 * Get the chest boat's {@link EntityType<ChestBoat>} object.
	 *
	 * @return EntityType of the chest boat.
	 */
	EntityType<ChestBoat> chestBoatEntityType();

	/**
	 * Get the boat's model layers.
	 *
	 * @return Identifier of the boat's entity model layers
	 */
	Identifier boatModelLayerId();

	/**
	 * Get the chest boat's model layers.
	 *
	 * @return Identifier of the chest boat's entity model layers
	 */
	Identifier chestBoatModelLayerId();


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
	ResourceKey<Item> raftKey();

	/**
	 * Get the chest raft item's registry key.
	 *
	 * @return RegistryKey of the chest raft item
	 */
	ResourceKey<Item> chestRaftKey();

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
	ResourceKey<EntityType<?>> raftEntityTypeKey();

	/**
	 * Get the chest raft entity type's registry key.
	 *
	 * @return RegistryKey of the chest raft entity type
	 */
	ResourceKey<EntityType<?>> chestRaftEntityTypeKey();

	/**
	 * Get the raft's {@link EntityType<Raft>} object.
	 *
	 * @return EntityType of the raft.
	 */
	EntityType<Raft> raftEntityType();

	/**
	 * Get the chest raft's {@link EntityType<ChestRaft>} object.
	 *
	 * @return EntityType of the chest raft.
	 */
	EntityType<ChestRaft> chestRaftEntityType();

	/**
	 * Get the raft's model layers.
	 *
	 * @return Identifier of the raft's entity model layers
	 */
	Identifier raftModelLayerId();

	/**
	 * Get the chest raft's model layers.
	 *
	 * @return Identifier of the chest raft's entity model layers
	 */
	Identifier chestRaftModelLayerId();
}
