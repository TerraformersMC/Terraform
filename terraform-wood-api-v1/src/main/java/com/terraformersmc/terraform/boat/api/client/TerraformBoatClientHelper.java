package com.terraformersmc.terraform.boat.api.client;

import com.terraformersmc.terraform.boat.impl.client.TerraformBoatClientHelperImpl;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

/**
 * This class provides useful helpers for registering a {@linkplain net.minecraft.entity.vehicle.BoatEntity boat} on the client.
 */
@Environment(EnvType.CLIENT)
@SuppressWarnings("unused")
public final class TerraformBoatClientHelper {
	private TerraformBoatClientHelper() {
		return;
	}

	/**
	 * Registers {@linkplain EntityModelLayer model layers} and
	 * {@linkplain BoatEntityRenderer entity renderers} for all boats of given boat type.
	 * The provided identifier must match the identifier used to
	 * {@linkplain com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper#registerBoatItem register the boat type}.
	 *
	 * <pre>{@code
	 *     TerraformBoatClientHelper.registerModelLayers(Identifier.of("examplemod", "mahogany"));
	 * }</pre>
	 *
	 * @param id the {@linkplain net.minecraft.util.Identifier identifier} of the boat type.
	 */
	public static void registerModelLayers(Identifier id) {
		TerraformBoatClientHelperImpl.registerModelLayers(id);
	}
}
