package com.terraformersmc.terraform.boat.impl.data;

import com.terraformersmc.terraform.boat.api.data.TerraformBoatData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.entity.vehicle.ChestRaftEntity;
import net.minecraft.entity.vehicle.RaftEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This internal implementation class provides data records used by
 * {@linkplain com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper Terraform boat helper}
 * to complete client-side registration of the entities, models, and layers.
 *
 * Immutable access is available via the {@linkplain TerraformBoatData} API class.
 */
public record TerraformBoatDataImpl(Identifier id, EntityType<BoatEntity> boatEntityType, EntityType<ChestBoatEntity> chestBoatEntityType, EntityType<RaftEntity> raftEntityType, EntityType<ChestRaftEntity> chestRaftEntityType) implements TerraformBoatData {
	private static final Map<Identifier, TerraformBoatDataImpl> BOAT_DATA = new ConcurrentHashMap<>();

	public TerraformBoatDataImpl {
		Objects.requireNonNull(id);
	}


	public static void put(TerraformBoatDataImpl boatData) {
		BOAT_DATA.put(boatData.id, boatData);
	}

	public static TerraformBoatDataImpl get(Identifier id) {
		Objects.requireNonNull(id);
		if (!BOAT_DATA.containsKey(id)) {
			throw new RuntimeException("Request for unregistered boat data: " + id);
		}

		return BOAT_DATA.get(id);
	}

	public static Optional<TerraformBoatDataImpl> getOptional(Identifier id) {
		if (id == null || !BOAT_DATA.containsKey(id)) {
			return Optional.empty();
		}

		return Optional.of(BOAT_DATA.get(id));
	}


	public static TerraformBoatDataImpl empty(Identifier id) {
		return new TerraformBoatDataImpl(id, null, null, null, null);
	}

	public static void addBoat(Identifier id, EntityType<BoatEntity> boatEntity) {
		if (BOAT_DATA.containsKey(id)) {
			TerraformBoatDataImpl old = BOAT_DATA.get(id);
			if (old.boatEntityType != null) {
				throw new IllegalStateException("Attempted to replace existing boat entity: " + old.boatId());
			}
			put(new TerraformBoatDataImpl(id, boatEntity, old.chestBoatEntityType, old.raftEntityType, old.chestRaftEntityType));
		} else {
			put(new TerraformBoatDataImpl(id, boatEntity, null, null, null));
		}
	}

	public static void addChestBoat(Identifier id, EntityType<ChestBoatEntity> chestBoatEntity) {
		if (BOAT_DATA.containsKey(id)) {
			TerraformBoatDataImpl old = BOAT_DATA.get(id);
			if (old.chestBoatEntityType != null) {
				throw new IllegalStateException("Attempted to replace existing chest boat entity: " + old.chestBoatId());
			}
			put(new TerraformBoatDataImpl(id, old.boatEntityType, chestBoatEntity, old.raftEntityType, old.chestRaftEntityType));
		} else {
			put(new TerraformBoatDataImpl(id, null, chestBoatEntity, null, null));
		}
	}

	public static void addRaft(Identifier id, EntityType<RaftEntity> raftEntity) {
		if (BOAT_DATA.containsKey(id)) {
			TerraformBoatDataImpl old = BOAT_DATA.get(id);
			if (old.raftEntityType != null) {
				throw new IllegalStateException("Attempted to replace existing raft entity: " + old.raftId());
			}
			put(new TerraformBoatDataImpl(id, old.boatEntityType, old.chestBoatEntityType, raftEntity, old.chestRaftEntityType));
		} else {
			put(new TerraformBoatDataImpl(id, null, null, raftEntity, null));
		}
	}

	public static void addChestRaft(Identifier id, EntityType<ChestRaftEntity> chestRaftEntity) {
		if (BOAT_DATA.containsKey(id)) {
			TerraformBoatDataImpl old = BOAT_DATA.get(id);
			if (old.chestRaftEntityType != null) {
				throw new IllegalStateException("Attempted to replace existing chest raft entity: " + old.chestRaftId());
			}
			put(new TerraformBoatDataImpl(id, old.boatEntityType, old.chestBoatEntityType, old.raftEntityType, chestRaftEntity));
		} else {
			put(new TerraformBoatDataImpl(id, null, null, null, chestRaftEntity));
		}
	}


	@Override
	public Identifier boatId() {
		return id.withSuffixedPath("_boat");
	}

	@Override
	public Identifier chestBoatId() {
		return id.withSuffixedPath("_chest_boat");
	}

	@Override
	public RegistryKey<Item> boatKey() {
		return RegistryKey.of(RegistryKeys.ITEM, boatId());
	}

	@Override
	public RegistryKey<Item> chestBoatKey() {
		return RegistryKey.of(RegistryKeys.ITEM, chestBoatId());
	}

	@Override
	public Identifier boatEntityTypeId() {
		return boatId();
	}

	@Override
	public Identifier chestBoatEntityTypeId() {
		return chestBoatId();
	}

	@Override
	public RegistryKey<EntityType<?>> boatEntityTypeKey() {
		return RegistryKey.of(RegistryKeys.ENTITY_TYPE, boatEntityTypeId());
	}

	@Override
	public RegistryKey<EntityType<?>> chestBoatEntityTypeKey() {
		return RegistryKey.of(RegistryKeys.ENTITY_TYPE, chestBoatEntityTypeId());
	}

	@Override
	public EntityModelLayer boatModelLayer() {
		return new EntityModelLayer(id.withPrefixedPath("boat/"), "main");
	}

	@Override
	public EntityModelLayer chestBoatModelLayer() {
		return new EntityModelLayer(id.withPrefixedPath("chest_boat/"), "main");
	}

	@Override
	public Identifier raftId() {
		return id.withSuffixedPath("_raft");
	}

	@Override
	public Identifier chestRaftId() {
		return id.withSuffixedPath("_chest_raft");
	}

	@Override
	public RegistryKey<Item> raftKey() {
		return RegistryKey.of(RegistryKeys.ITEM, raftId());
	}

	@Override
	public RegistryKey<Item> chestRaftKey() {
		return RegistryKey.of(RegistryKeys.ITEM, chestRaftId());
	}

	@Override
	public Identifier raftEntityTypeId() {
		return raftId();
	}

	@Override
	public Identifier chestRaftEntityTypeId() {
		return chestRaftId();
	}

	@Override
	public RegistryKey<EntityType<?>> raftEntityTypeKey() {
		return RegistryKey.of(RegistryKeys.ENTITY_TYPE, raftEntityTypeId());
	}

	@Override
	public RegistryKey<EntityType<?>> chestRaftEntityTypeKey() {
		return RegistryKey.of(RegistryKeys.ENTITY_TYPE, chestRaftEntityTypeId());
	}

	@Override
	public EntityModelLayer raftModelLayer() {
		return new EntityModelLayer(id.withPrefixedPath("raft/"), "main");
	}

	@Override
	public EntityModelLayer chestRaftModelLayer() {
		return new EntityModelLayer(id.withPrefixedPath("chest_raft/"), "main");
	}
}
