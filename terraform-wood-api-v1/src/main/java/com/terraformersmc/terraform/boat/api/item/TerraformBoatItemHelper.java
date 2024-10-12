package com.terraformersmc.terraform.boat.api.item;

import com.terraformersmc.terraform.boat.impl.item.TerraformBoatItemHelperImpl;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public final class TerraformBoatItemHelper {
	private TerraformBoatItemHelper() {
		return;
	}

	/**
	 * Creates and registers a {@linkplain BoatItem boat item} and associated
	 * {@linkplain net.minecraft.entity.vehicle.BoatEntity boat entity} of the requested type
	 * and with the default {@linkplain Item.Settings}.  This method assumes the type is a boat
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
	 * {@linkplain net.minecraft.entity.vehicle.BoatEntity boat entity} of the requested type
	 * and with the default {@linkplain Item.Settings}.
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
		return registerBoatItem(id, new Item.Settings().maxCount(1), chest, raft);
	}

	/**
	 * Creates and registers a {@linkplain BoatItem boat item} and associated
	 * {@linkplain net.minecraft.entity.vehicle.BoatEntity boat entity} of the requested type
	 * and with the provided {@linkplain Item.Settings}.
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
	public static BoatItem registerBoatItem(Identifier id, Item.Settings settings, boolean chest, boolean raft) {
		return TerraformBoatItemHelperImpl.registerBoatItem(id, settings, chest, raft);
	}

	/**
	 * Registers a vanilla {@link net.minecraft.block.dispenser.BoatDispenserBehavior boat dispenser behavior}
	 * for the provided {@linkplain ItemConvertible item} and
	 * {@linkplain net.minecraft.entity.vehicle.BoatEntity boat entity}.
	 *
	 * This registration is performed automatically by the {@linkplain TerraformBoatItemHelper#registerBoatItem}
	 * methods of this class.
	 *
	 * @param item The item for which to register the dispenser behavior
	 * @param boatEntity The boat entity which should be dispensed
	 */
	public static void registerBoatDispenserBehavior(ItemConvertible item, EntityType<? extends AbstractBoatEntity> boatEntity) {
		TerraformBoatItemHelperImpl.registerBoatDispenserBehavior(item, boatEntity);
	}
}
