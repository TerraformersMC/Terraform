package com.terraformersmc.terraform.boat.api.item;

import java.util.function.Supplier;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.impl.TerraformBoatItem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * This class provides utilities to {@linkplain #registerBoatItem(Identifier, Supplier, Item.Settings) register}
 * the {@linkplain TerraformBoatItem item forms} of {@linkplain TerraformBoatType Terraform boats}.
 */
public final class TerraformBoatItemHelper {
	private static final ItemGroup DEFAULT_ITEM_GROUP = ItemGroup.TRANSPORTATION;

	private TerraformBoatItemHelper() {
		return;
	}

	/**
	 * Registers a {@linkplain TerraformBoatItem boat item} with the default item group ({@value #DEFAULT_ITEM_GROUP}).
	 * 
	 * <p>To register a boat item:
	 * 
	 * <pre>{@code
	 *     TerraformBoatItemHelper.registerBoatItem(new Identifier("examplemod", "mahogany_boat"), () -> MAHOGANY_BOAT);
	 * }</pre>
	 * 
	 * @see #registerBoatItem(Identifier, Supplier, ItemGroup) Helper that allows specifying a custom item group
	 * @see #registerBoatItem(Identifier, Supplier, Item.Settings) Helper that allows specifying a custom item settings
	 * 
	 * @param id the {@linkplain Identifier identifier} to register the item with 
	 * @param boatSupplier a {@linkplain Supplier supplier} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this item
	 */
	public static Item registerBoatItem(Identifier id, Supplier<TerraformBoatType> boatSupplier) {
		return registerBoatItem(id, boatSupplier, DEFAULT_ITEM_GROUP);
	}

	/**
	 * Registers a {@linkplain TerraformBoatItem boat item} with a specified item group.
	 * 
	 * <p>To register a boat item:
	 * 
	 * <pre>{@code
	 *     TerraformBoatItemHelper.registerBoatItem(new Identifier("examplemod", "mahogany_boat"), () -> MAHOGANY_BOAT, ItemGroup.TRANSPORTATION);
	 * }</pre>
	 * 
	 * @see #registerBoatItem(Identifier, Supplier, Item.Settings) Helper that allows specifying a custom item settings
	 * 
	 * @param id the {@linkplain Identifier identifier} to register the item with
	 * @param boatSupplier a {@linkplain Supplier supplier} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this item
	 */
	public static Item registerBoatItem(Identifier id, Supplier<TerraformBoatType> boatSupplier, ItemGroup group) {
		return registerBoatItem(id, boatSupplier, new Item.Settings().maxCount(1).group(group));
	}

	/**
	 * Registers a {@linkplain TerraformBoatItem boat item}.
	 * 
	 * <p>To register a boat item:
	 * 
	 * <pre>{@code
	 *     TerraformBoatItemHelper.registerBoatItem(new Identifier("examplemod", "mahogany_boat"), () -> MAHOGANY_BOAT, new Item.Settings().maxCount(1).group(ItemGroup.TRANSPORTATION));
	 * }</pre>
	 * 
	 * @param id the {@linkplain Identifier identifier} to register the item with
	 * @param boatSupplier a {@linkplain Supplier supplier} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this item
	 */
	public static Item registerBoatItem(Identifier id, Supplier<TerraformBoatType> boatSupplier, Item.Settings settings) {
		Item item = new TerraformBoatItem(boatSupplier, settings);
		Registry.register(Registry.ITEM, id, item);

		return item;
	}
}
