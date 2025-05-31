package com.terraformersmc.terraform.boat.impl.item;

import com.terraformersmc.terraform.boat.impl.data.TerraformBoatDataImpl;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.BoatDispenserBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.vehicle.*;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class TerraformBoatItemHelperImpl {
	private TerraformBoatItemHelperImpl() {
		return;
	}

	private static EntityType.EntityFactory<BoatEntity> getBoatFactory(Supplier<Item> itemSupplier) {
		return (type, world) -> new BoatEntity(type, world, itemSupplier);
	}

	private static EntityType.EntityFactory<ChestBoatEntity> getChestBoatFactory(Supplier<Item> itemSupplier) {
		return (type, world) -> new ChestBoatEntity(type, world, itemSupplier);
	}

	private static EntityType.EntityFactory<RaftEntity> getRaftFactory(Supplier<Item> itemSupplier) {
		return (type, world) -> new RaftEntity(type, world, itemSupplier);
	}

	private static EntityType.EntityFactory<ChestRaftEntity> getChestRaftFactory(Supplier<Item> itemSupplier) {
		return (type, world) -> new ChestRaftEntity(type, world, itemSupplier);
	}

	private static <T extends Entity> EntityType.Builder<T> createEntityTypeBuilder(EntityType.EntityFactory<T> factory) {
		return EntityType.Builder.create(factory, SpawnGroup.MISC)
				.dropsNothing()
				.dimensions(1.375f, 0.5625f)
				.eyeHeight(0.5625f)
				.maxTrackingRange(10);
	}

	private static <T extends Entity> EntityType<T> registerEntityType(RegistryKey<EntityType<?>> key, EntityType.Builder<T> type) {
		return Registry.register(Registries.ENTITY_TYPE, key, type.build(key));
	}

	private static <T extends AbstractBoatEntity> BoatItem registerBoat(Identifier id, RegistryKey<Item> itemKey, RegistryKey<EntityType<?>> entityTypeKey, Item.Settings settings, Function<Supplier<Item>, EntityType.EntityFactory<T>> factory, BiConsumer<Identifier, EntityType<T>> registry) {
		DelayedItemSupplier itemSupplier = new DelayedItemSupplier();
		EntityType<T> entityType = registerEntityType(entityTypeKey, createEntityTypeBuilder(factory.apply(itemSupplier)));
		BoatItem item = Registry.register(Registries.ITEM, itemKey, new BoatItem(entityType, settings.registryKey(itemKey)));
		itemSupplier.set(item);

		registry.accept(id, entityType);
		registerBoatDispenserBehavior(item, entityType);

		return item;
	}


	public static BoatItem registerBoatItem(Identifier id, Item.Settings settings, boolean chest, boolean raft) {
		TerraformBoatDataImpl boatData = TerraformBoatDataImpl.empty(id);

		if (raft) {
			if (chest) {
				return registerBoat(id, boatData.chestRaftKey(), boatData.chestRaftEntityTypeKey(), settings,
						TerraformBoatItemHelperImpl::getChestRaftFactory, TerraformBoatDataImpl::addChestRaft);
			} else {
				return registerBoat(id, boatData.raftKey(), boatData.raftEntityTypeKey(), settings,
						TerraformBoatItemHelperImpl::getRaftFactory, TerraformBoatDataImpl::addRaft);
			}
		} else {
			if (chest) {
				return registerBoat(id, boatData.chestBoatKey(), boatData.chestBoatEntityTypeKey(), settings,
						TerraformBoatItemHelperImpl::getChestBoatFactory, TerraformBoatDataImpl::addChestBoat);
			} else {
				return registerBoat(id, boatData.boatKey(), boatData.boatEntityTypeKey(), settings,
						TerraformBoatItemHelperImpl::getBoatFactory, TerraformBoatDataImpl::addBoat);
			}
		}
	}

	public static void registerBoatDispenserBehavior(ItemConvertible item, EntityType<? extends AbstractBoatEntity> boatEntity) {
		DispenserBlock.registerBehavior(item, new BoatDispenserBehavior(boatEntity));
	}


	private static class DelayedItemSupplier implements Supplier<Item> {
		Item value = Items.AIR;

		public void set(Item value) {
			this.value = value;
		}

		@Override
		public Item get() {
			return this.value;
		}
	}
}
