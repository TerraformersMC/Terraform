package com.terraformersmc.terraform.boat.api.client;

import com.terraformersmc.terraform.boat.impl.client.TerraformBoatClientHelperImpl;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.resources.Identifier;

/**
 * This class provides useful helpers for registering a {@linkplain net.minecraft.world.entity.vehicle.boat.Boat boat} on the client.
 */
@Environment(EnvType.CLIENT)
@SuppressWarnings("unused")
public final class TerraformBoatClientHelper {
	private TerraformBoatClientHelper() {
		return;
	}

	/**
	 * Registers {@linkplain ModelLayerLocation model layers} and
	 * {@linkplain BoatRenderer entity renderers} for all boats of given boat type.
	 * The provided identifier must match the identifier used to
	 * {@linkplain com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper#registerBoatItem register the boat type}.
	 *
	 * <pre>{@code
	 *     TerraformBoatClientHelper.registerModelLayers(Identifier.of("examplemod", "mahogany"));
	 * }</pre>
	 *
	 * @param id the {@linkplain net.minecraft.resources.Identifier identifier} of the boat type.
	 */
	public static void registerModelLayers(Identifier id) {
		TerraformBoatClientHelperImpl.registerModelLayers(id);
	}
}
