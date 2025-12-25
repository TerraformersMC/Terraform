package com.terraformersmc.terraform.boat.impl.item;

import com.terraformersmc.terraform.boat.impl.data.TerraformBoatDataImpl;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.BoatDispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.entity.vehicle.boat.ChestRaft;
import net.minecraft.world.entity.vehicle.boat.Raft;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.DispenserBlock;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class TerraformBoatItemHelperImpl {
	private TerraformBoatItemHelperImpl() {
		return;
	}

	private static EntityType.EntityFactory<Boat> getBoatFactory(Supplier<Item> itemSupplier) {
		return (type, world) -> new Boat(type, world, itemSupplier);
	}

	private static EntityType.EntityFactory<ChestBoat> getChestBoatFactory(Supplier<Item> itemSupplier) {
		return (type, world) -> new ChestBoat(type, world, itemSupplier);
	}

	private static EntityType.EntityFactory<Raft> getRaftFactory(Supplier<Item> itemSupplier) {
		return (type, world) -> new Raft(type, world, itemSupplier);
	}

	private static EntityType.EntityFactory<ChestRaft> getChestRaftFactory(Supplier<Item> itemSupplier) {
		return (type, world) -> new ChestRaft(type, world, itemSupplier);
	}

	private static <T extends Entity> EntityType.Builder<T> createEntityTypeBuilder(EntityType.EntityFactory<T> factory) {
		return EntityType.Builder.of(factory, MobCategory.MISC)
				.noLootTable()
				.sized(1.375f, 0.5625f)
				.eyeHeight(0.5625f)
				.clientTrackingRange(10);
	}

	private static <T extends Entity> EntityType<T> registerEntityType(ResourceKey<EntityType<?>> key, EntityType.Builder<T> type) {
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, type.build(key));
	}

	private static <T extends AbstractBoat> BoatItem registerBoat(Identifier id, ResourceKey<Item> itemKey, ResourceKey<EntityType<?>> entityTypeKey, Item.Properties settings, Function<Supplier<Item>, EntityType.EntityFactory<T>> factory, BiConsumer<Identifier, EntityType<T>> registry) {
		DelayedItemSupplier itemSupplier = new DelayedItemSupplier();
		EntityType<T> entityType = registerEntityType(entityTypeKey, createEntityTypeBuilder(factory.apply(itemSupplier)));
		BoatItem item = Registry.register(BuiltInRegistries.ITEM, itemKey, new BoatItem(entityType, settings.setId(itemKey)));
		itemSupplier.set(item);

		registry.accept(id, entityType);
		registerBoatDispenserBehavior(item, entityType);

		return item;
	}


	public static BoatItem registerBoatItem(Identifier id, Item.Properties settings, boolean chest, boolean raft) {
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

	public static void registerBoatDispenserBehavior(ItemLike item, EntityType<? extends AbstractBoat> boatEntity) {
		DispenserBlock.registerBehavior(item, new BoatDispenseItemBehavior(boatEntity));
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
