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
 * such as {@linkplain #registerBoatItem(Identifier, Supplier, Item.Settings) registering them and their dispenser behavior}.
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
	 *     TerraformBoatItemHelper.registerBoatItem(new Identifier("examplemod", "mahogany_boat"), () -> MAHOGANY_BOAT);
	 * }</pre>
	 * 
	 * @see #registerBoatItem(Identifier, Supplier, ItemGroup) Helper that allows specifying a custom item group
	 * @see #registerBoatItem(Identifier, Supplier, Item.Settings) Helper that allows specifying a custom item settings
	 * 
	 * @param id the {@linkplain Identifier identifier} to register the item with 
	 * @param boatSupplier a {@linkplain Supplier supplier} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this item and dispenser behavior
	 */
	public static Item registerBoatItem(Identifier id, Supplier<TerraformBoatType> boatSupplier) {
		return registerBoatItem(id, boatSupplier, DEFAULT_ITEM_GROUP);
	}

	/**
	 * Registers a {@linkplain TerraformBoatItem boat item}
	 * with a specified item group
	 * and its corresponding {@link #registerBoatDispenserBehavior dispenser behavior}.
	 * 
	 * <p>To register a boat item and its dispenser behavior:
	 * 
	 * <pre>{@code
	 *     TerraformBoatItemHelper.registerBoatItem(new Identifier("examplemod", "mahogany_boat"), () -> MAHOGANY_BOAT, ItemGroup.TRANSPORTATION);
	 * }</pre>
	 * 
	 * @see #registerBoatItem(Identifier, Supplier, Item.Settings) Helper that allows specifying a custom item settings
	 * 
	 * @param id the {@linkplain Identifier identifier} to register the item with
	 * @param boatSupplier a {@linkplain Supplier supplier} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this item and dispenser behavior
	 */
	public static Item registerBoatItem(Identifier id, Supplier<TerraformBoatType> boatSupplier, ItemGroup group) {
		return registerBoatItem(id, boatSupplier, new Item.Settings().maxCount(1).group(group));
	}

	/**
	 * Registers a {@linkplain TerraformBoatItem boat item} and its corresponding {@link #registerBoatDispenserBehavior dispenser behavior}.
	 * 
	 * <p>To register a boat item and its dispenser behavior:
	 * 
	 * <pre>{@code
	 *     TerraformBoatItemHelper.registerBoatItem(new Identifier("examplemod", "mahogany_boat"), () -> MAHOGANY_BOAT, new Item.Settings().maxCount(1).group(ItemGroup.TRANSPORTATION));
	 * }</pre>
	 * 
	 * @param id the {@linkplain Identifier identifier} to register the item with
	 * @param boatSupplier a {@linkplain Supplier supplier} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this item and dispenser behavior
	 */
	public static Item registerBoatItem(Identifier id, Supplier<TerraformBoatType> boatSupplier, Item.Settings settings) {
		Item item = new TerraformBoatItem(boatSupplier, settings);
		Registry.register(Registry.ITEM, id, item);

		registerBoatDispenserBehavior(item, boatSupplier);
		return item;
	}

	/**
	 * Registers a {@linkplain net.minecraft.block.dispenser.DispenserBehavior dispenser behavior} that spawns a {@linkplain com.terraformersmc.terraform.boat.impl.TerraformBoatEntity boat entity} with a given {@linkplain TerraformBoatType Terraform boat type}.
	 * 
	 * <p>To register a boat dispenser behavior:
	 * 
	 * <pre>{@code
	 *     TerraformBoatItemHelper.registerBoatDispenserBehavior(MAHOGANY_BOAT_ITEM, () -> MAHOGANY_BOAT);
	 * }</pre>
	 * 
	 * @param item the item that should be assigned to this dispenser behavior
	 * @param boatSupplier a {@linkplain Supplier supplier} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this dispenser behavior
	 */
	public static void registerBoatDispenserBehavior(ItemConvertible item, Supplier<TerraformBoatType> boatSupplier) {
		DispenserBlock.registerBehavior(item, new TerraformBoatDispenserBehavior(boatSupplier));
	}
}
