package com.terraformersmc.terraform.boat.api.item;

import java.util.function.Supplier;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.impl.item.TerraformBoatDispenserBehavior;
import com.terraformersmc.terraform.boat.impl.item.TerraformBoatItem;

import net.minecraft.block.DispenserBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * This class provides utilities for the {@linkplain TerraformBoatItem item forms} of {@linkplain TerraformBoatType Terraform boats},
 * such as {@linkplain #registerBoatItem(Identifier, Supplier, boolean, Item.Settings) registering them and their dispenser behavior}.
 */
public final class TerraformBoatItemHelper {
	private static final ItemGroup DEFAULT_ITEM_GROUP = ItemGroup.TRANSPORTATION;

	private TerraformBoatItemHelper() {
		return;
	}

	/**
	 * Registers a {@linkplain TerraformBoatItem boat item}
	 * with the default item group ({@link ItemGroup#TRANSPORTATION})
	 * and its corresponding {@link #registerBoatDispenserBehavior dispenser behavior}.
	 * 
	 * <p>To register a boat item and its dispenser behavior:
	 * 
	 * <pre>{@code
	 *     TerraformBoatItemHelper.registerBoatItem(new Identifier("examplemod", "mahogany_boat"), () -> MAHOGANY_BOAT, false);
	 * }</pre>
	 * 
	 * <p>This method should be called twice for a given boat type for both boats and chest boats.
	 * 
	 * @see #registerBoatItem(Identifier, Supplier, boolean, ItemGroup) Helper that allows specifying a custom item group
	 * @see #registerBoatItem(Identifier, Supplier, boolean, Item.Settings) Helper that allows specifying a custom item settings
	 * 
	 * @param id the {@linkplain Identifier identifier} to register the item with 
	 * @param boatSupplier a {@linkplain Supplier supplier} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this item and dispenser behavior
	 * @param chest whether the boat contains a chest
	 */
	public static Item registerBoatItem(Identifier id, Supplier<TerraformBoatType> boatSupplier, boolean chest) {
		return registerBoatItem(id, boatSupplier, chest, DEFAULT_ITEM_GROUP);
	}

	/**
	 * Registers a {@linkplain TerraformBoatItem boat item}
	 * with a specified item group
	 * and its corresponding {@link #registerBoatDispenserBehavior dispenser behavior}.
	 * 
	 * <p>To register a boat item and its dispenser behavior:
	 * 
	 * <pre>{@code
	 *     TerraformBoatItemHelper.registerBoatItem(new Identifier("examplemod", "mahogany_boat"), () -> MAHOGANY_BOAT, false, ItemGroup.TRANSPORTATION);
	 * }</pre>
	 * 
	 * <p>This method should be called twice for a given boat type for both boats and chest boats.
	 * 
	 * @see #registerBoatItem(Identifier, Supplier, false, Item.Settings) Helper that allows specifying a custom item settings
	 * 
	 * @param id the {@linkplain Identifier identifier} to register the item with
	 * @param boatSupplier a {@linkplain Supplier supplier} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this item and dispenser behavior
	 */
	public static Item registerBoatItem(Identifier id, Supplier<TerraformBoatType> boatSupplier, boolean chest, ItemGroup group) {
		return registerBoatItem(id, boatSupplier, chest, new Item.Settings().maxCount(1).group(group));
	}

	/**
	 * Registers a {@linkplain TerraformBoatItem boat item} and its corresponding {@link #registerBoatDispenserBehavior dispenser behavior}.
	 * 
	 * <p>To register a boat item and its dispenser behavior:
	 * 
	 * <pre>{@code
	 *     TerraformBoatItemHelper.registerBoatItem(new Identifier("examplemod", "mahogany_boat"), () -> MAHOGANY_BOAT, false, new Item.Settings().maxCount(1).group(ItemGroup.TRANSPORTATION));
	 * }</pre>
	 * 
	 * <p>This method should be called twice for a given boat type for both boats and chest boats.
	 * 
	 * @param id the {@linkplain Identifier identifier} to register the item with
	 * @param boatSupplier a {@linkplain Supplier supplier} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this item and dispenser behavior
	 * @param chest whether the boat contains a chest
	 */
	public static Item registerBoatItem(Identifier id, Supplier<TerraformBoatType> boatSupplier, boolean chest, Item.Settings settings) {
		Item item = new TerraformBoatItem(boatSupplier, chest, settings);
		Registry.register(Registry.ITEM, id, item);

		registerBoatDispenserBehavior(item, boatSupplier, chest);
		return item;
	}

	/**
	 * Registers a {@linkplain net.minecraft.block.dispenser.DispenserBehavior dispenser behavior} that spawns a {@linkplain com.terraformersmc.terraform.boat.impl.entity.TerraformBoatEntity boat entity} with a given {@linkplain TerraformBoatType Terraform boat type}.
	 * 
	 * <p>To register a boat dispenser behavior:
	 * 
	 * <pre>{@code
	 *     TerraformBoatItemHelper.registerBoatDispenserBehavior(MAHOGANY_BOAT_ITEM, () -> MAHOGANY_BOAT, false);
	 * }</pre>
	 * 
	 * <p>This method should be called twice for a given boat type for both boats and chest boats.
	 * 
	 * @param item the item that should be assigned to this dispenser behavior
	 * @param boatSupplier a {@linkplain Supplier supplier} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this dispenser behavior
	 * @param chest whether the boat contains a chest
	 */
	public static void registerBoatDispenserBehavior(ItemConvertible item, Supplier<TerraformBoatType> boatSupplier, boolean chest) {
		DispenserBlock.registerBehavior(item, new TerraformBoatDispenserBehavior(boatSupplier, chest));
	}
}
