package com.terraformersmc.terraform.boat.api.item;

import com.terraformersmc.terraform.boat.impl.item.TerraformBoatItemHelperImpl;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

@SuppressWarnings("unused")
public final class TerraformBoatItemHelper {
	private TerraformBoatItemHelper() {
		return;
	}

	/**
	 * Creates and registers a {@linkplain BoatItem boat item} and associated
	 * {@linkplain net.minecraft.world.entity.vehicle.boat.Boat boat entity} of the requested type
	 * and with the default {@linkplain Item.Properties}.  This method assumes the type is a boat
	 * instead of a raft.
	 *
	 * This method should be called once for each boat type.
	 * Created items and entities will have identifiers similar to
	 * {@code id.withSuffixedPath("_boat")} and {@code id.withPrefixedPath("chest_raft/")}.
	 *
	 * <pre>{@code
	 *     BoatItem boat = TerraformBoatItemHelper.registerBoatItem(Identifier.of("examplemod", "mahogany"), false);
	 *     BoatItem chestBoat = TerraformBoatItemHelper.registerBoatItem(Identifier.of("examplemod", "mahogany"), true);
	 * }</pre>
	 *
	 * @param id The identifier of the boat family
	 * @param chest Whether the boat is a chest boat
	 * @return The created, registered boat item
	 */
	public static BoatItem registerBoatItem(Identifier id, boolean chest) {
		return registerBoatItem(id, chest, false);
	}

	/**
	 * Creates and registers a {@linkplain BoatItem boat item} and associated
	 * {@linkplain net.minecraft.world.entity.vehicle.boat.Boat boat entity} of the requested type
	 * and with the default {@linkplain Item.Properties}.
	 *
	 * This method should be called once for each boat type.  Both boat and raft may be registered
	 * for the same wood type, if desired.  Created items and entities will have identifiers similar to
	 * {@code id.withSuffixedPath("_boat")} and {@code id.withPrefixedPath("chest_raft/")}.
	 *
	 * <pre>{@code
	 *     BoatItem boat = TerraformBoatItemHelper.registerBoatItem(Identifier.of("examplemod", "mahogany"), false, false);
	 *     BoatItem chestBoat = TerraformBoatItemHelper.registerBoatItem(Identifier.of("examplemod", "mahogany"), true, false);
	 * }</pre>
	 *
	 * @param id The identifier of the boat family
	 * @param chest Whether the boat is a chest boat
	 * @param raft Whether the boat is a raft
	 * @return The created, registered boat item
	 */
	public static BoatItem registerBoatItem(Identifier id, boolean chest, boolean raft) {
		return registerBoatItem(id, new Item.Properties().stacksTo(1), chest, raft);
	}

	/**
	 * Creates and registers a {@linkplain BoatItem boat item} and associated
	 * {@linkplain net.minecraft.world.entity.vehicle.boat.Boat boat entity} of the requested type
	 * and with the provided {@linkplain Item.Properties}.
	 *
	 * This method should be called once for each boat type.  Both boat and raft may be registered
	 * for the same wood type, if desired.  Created items and entities will have identifiers similar to
	 * {@code id.withSuffixedPath("_boat")} and {@code id.withPrefixedPath("chest_raft/")}.
	 *
	 * <pre>{@code
	 *     BoatItem boat = TerraformBoatItemHelper.registerBoatItem(Identifier.of("examplemod", "mahogany"), settings, false, false);
	 *     BoatItem chestBoat = TerraformBoatItemHelper.registerBoatItem(Identifier.of("examplemod", "mahogany"), settings, true, false);
	 * }</pre>
	 *
	 * @param id The identifier of the boat family
	 * @param settings Non-default item settings (f.e. changing stack size)
	 * @param chest Whether the boat is a chest boat
	 * @param raft Whether the boat is a raft
	 * @return The created, registered boat item
	 */
	public static BoatItem registerBoatItem(Identifier id, Item.Properties settings, boolean chest, boolean raft) {
		return TerraformBoatItemHelperImpl.registerBoatItem(id, settings, chest, raft);
	}

	/**
	 * Registers a vanilla {@link net.minecraft.core.dispenser.BoatDispenseItemBehavior boat dispenser behavior}
	 * for the provided {@linkplain ItemLike item} and
	 * {@linkplain net.minecraft.world.entity.vehicle.boat.Boat boat entity}.
	 *
	 * This registration is performed automatically by the {@linkplain TerraformBoatItemHelper#registerBoatItem}
	 * methods of this class.
	 *
	 * @param item The item for which to register the dispenser behavior
	 * @param boatEntity The boat entity which should be dispensed
	 */
	public static void registerBoatDispenserBehavior(ItemLike item, EntityType<? extends AbstractBoat> boatEntity) {
		TerraformBoatItemHelperImpl.registerBoatDispenserBehavior(item, boatEntity);
	}
}
