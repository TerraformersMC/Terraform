package com.terraformersmc.terraform.boat.impl.data;

import com.terraformersmc.terraform.boat.api.data.TerraformBoatData;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.entity.vehicle.boat.ChestRaft;
import net.minecraft.world.entity.vehicle.boat.Raft;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.Nullable;

/**
 * This internal implementation class provides data records used by TerraformBoatClientHelper
 * to complete client-side registration of the entities, models, and layers.
 * <p/>
 * Immutable access is available via the {@linkplain TerraformBoatData} API class.
 */
public record TerraformBoatDataImpl(Identifier id, @Nullable EntityType<Boat> boatEntityType, @Nullable EntityType<ChestBoat> chestBoatEntityType, @Nullable EntityType<Raft> raftEntityType, @Nullable EntityType<ChestRaft> chestRaftEntityType) implements TerraformBoatData {
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

	public static Optional<TerraformBoatDataImpl> getOptional(@Nullable Identifier id) {
		if (id == null || !BOAT_DATA.containsKey(id)) {
			return Optional.empty();
		}

		return Optional.of(BOAT_DATA.get(id));
	}


	public static TerraformBoatDataImpl empty(Identifier id) {
		return new TerraformBoatDataImpl(id, null, null, null, null);
	}

	public static void addBoat(Identifier id, EntityType<Boat> boatEntity) {
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

	public static void addChestBoat(Identifier id, EntityType<ChestBoat> chestBoatEntity) {
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

	public static void addRaft(Identifier id, EntityType<Raft> raftEntity) {
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

	public static void addChestRaft(Identifier id, EntityType<ChestRaft> chestRaftEntity) {
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
		return id.withSuffix("_boat");
	}

	@Override
	public Identifier chestBoatId() {
		return id.withSuffix("_chest_boat");
	}

	@Override
	public ResourceKey<Item> boatKey() {
		return ResourceKey.create(Registries.ITEM, boatId());
	}

	@Override
	public ResourceKey<Item> chestBoatKey() {
		return ResourceKey.create(Registries.ITEM, chestBoatId());
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
	public ResourceKey<EntityType<?>> boatEntityTypeKey() {
		return ResourceKey.create(Registries.ENTITY_TYPE, boatEntityTypeId());
	}

	@Override
	public ResourceKey<EntityType<?>> chestBoatEntityTypeKey() {
		return ResourceKey.create(Registries.ENTITY_TYPE, chestBoatEntityTypeId());
	}

	@Override
	public Identifier boatModelLayerId() {
		return id.withPrefix("boat/");
	}

	@Override
	public Identifier chestBoatModelLayerId() {
		return id.withPrefix("chest_boat/");
	}

	@Override
	public Identifier raftId() {
		return id.withSuffix("_raft");
	}

	@Override
	public Identifier chestRaftId() {
		return id.withSuffix("_chest_raft");
	}

	@Override
	public ResourceKey<Item> raftKey() {
		return ResourceKey.create(Registries.ITEM, raftId());
	}

	@Override
	public ResourceKey<Item> chestRaftKey() {
		return ResourceKey.create(Registries.ITEM, chestRaftId());
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
	public ResourceKey<EntityType<?>> raftEntityTypeKey() {
		return ResourceKey.create(Registries.ENTITY_TYPE, raftEntityTypeId());
	}

	@Override
	public ResourceKey<EntityType<?>> chestRaftEntityTypeKey() {
		return ResourceKey.create(Registries.ENTITY_TYPE, chestRaftEntityTypeId());
	}

	@Override
	public Identifier raftModelLayerId() {
		return id.withPrefix("raft/");
	}

	@Override
	public Identifier chestRaftModelLayerId() {
		return id.withPrefix("chest_raft/");
	}
}
