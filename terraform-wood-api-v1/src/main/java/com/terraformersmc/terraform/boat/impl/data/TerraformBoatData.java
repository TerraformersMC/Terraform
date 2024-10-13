package com.terraformersmc.terraform.boat.impl.data;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.*;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This internal implementation class provides data records used by
 * {@linkplain com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper Terraform boat helper}
 * to complete client-side registration of the entities, models, and layers.
 */
public record TerraformBoatData(Identifier id, EntityType<BoatEntity> boatEntity, EntityType<ChestBoatEntity> chestBoatEntity, EntityType<RaftEntity> raftEntity, EntityType<ChestRaftEntity> chestRaftEntity) {
	private static final Map<Identifier, TerraformBoatData> BOAT_DATA = new ConcurrentHashMap<>();

	public TerraformBoatData {
		Objects.requireNonNull(id);
	}

	public static void put(TerraformBoatData boatData) {
		BOAT_DATA.put(boatData.id, boatData);
	}

	public static TerraformBoatData get(Identifier id) {
		Objects.requireNonNull(id);
		if (!BOAT_DATA.containsKey(id)) {
			throw new RuntimeException("Request for unregistered boat data: " + id);
		}

		return BOAT_DATA.get(id);
	}

	public static TerraformBoatData empty(Identifier id) {
		return new TerraformBoatData(id, null, null, null, null);
	}

	public static void addBoat(Identifier id, EntityType<BoatEntity> boatEntity) {
		if (BOAT_DATA.containsKey(id)) {
			TerraformBoatData old = BOAT_DATA.get(id);
			if (old.boatEntity != null) {
				throw new IllegalStateException("Attempted to replace existing boat entity: " + old.boatId());
			}
			put(new TerraformBoatData(id, boatEntity, old.chestBoatEntity, old.raftEntity, old.chestRaftEntity));
		} else {
			put(new TerraformBoatData(id, boatEntity, null, null, null));
		}
	}

	public static void addChestBoat(Identifier id, EntityType<ChestBoatEntity> chestBoatEntity) {
		if (BOAT_DATA.containsKey(id)) {
			TerraformBoatData old = BOAT_DATA.get(id);
			if (old.chestBoatEntity != null) {
				throw new IllegalStateException("Attempted to replace existing chest boat entity: " + old.chestBoatId());
			}
			put(new TerraformBoatData(id, old.boatEntity, chestBoatEntity, old.raftEntity, old.chestRaftEntity));
		} else {
			put(new TerraformBoatData(id, null, chestBoatEntity, null, null));
		}
	}

	public static void addRaft(Identifier id, EntityType<RaftEntity> raftEntity) {
		if (BOAT_DATA.containsKey(id)) {
			TerraformBoatData old = BOAT_DATA.get(id);
			if (old.raftEntity != null) {
				throw new IllegalStateException("Attempted to replace existing raft entity: " + old.raftId());
			}
			put(new TerraformBoatData(id, old.boatEntity, old.chestBoatEntity, raftEntity, old.chestRaftEntity));
		} else {
			put(new TerraformBoatData(id, null, null, raftEntity, null));
		}
	}

	public static void addChestRaft(Identifier id, EntityType<ChestRaftEntity> chestRaftEntity) {
		if (BOAT_DATA.containsKey(id)) {
			TerraformBoatData old = BOAT_DATA.get(id);
			if (old.chestRaftEntity != null) {
				throw new IllegalStateException("Attempted to replace existing chest raft entity: " + old.chestRaftId());
			}
			put(new TerraformBoatData(id, old.boatEntity, old.chestBoatEntity, old.raftEntity, chestRaftEntity));
		} else {
			put(new TerraformBoatData(id, null, null, null, chestRaftEntity));
		}
	}

	public Identifier boatId() {
		return id.withSuffixedPath("_boat");
	}

	public Identifier chestBoatId() {
		return id.withSuffixedPath("_chest_boat");
	}

	public EntityModelLayer boatModelLayer() {
		return new EntityModelLayer(id.withPrefixedPath("boat/"), "main");
	}

	public EntityModelLayer chestBoatModelLayer() {
		return new EntityModelLayer(id.withPrefixedPath("chest_boat/"), "main");
	}

	public Identifier raftId() {
		return id.withSuffixedPath("_raft");
	}

	public Identifier chestRaftId() {
		return id.withSuffixedPath("_chest_raft");
	}

	public EntityModelLayer raftModelLayer() {
		return new EntityModelLayer(id.withPrefixedPath("raft/"), "main");
	}

	public EntityModelLayer chestRaftModelLayer() {
		return new EntityModelLayer(id.withPrefixedPath("chest_raft/"), "main");
	}
}
