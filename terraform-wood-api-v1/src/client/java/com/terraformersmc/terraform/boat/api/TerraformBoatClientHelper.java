package com.terraformersmc.terraform.boat.api;

import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import com.terraformersmc.terraform.boat.impl.TerraformBoatClientHelperImpl;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.resources.Identifier;

/**
 * This class provides useful helpers for registering plain or chest variants of
 * boats and rafts on the client.
 */
@SuppressWarnings("unused")
public final class TerraformBoatClientHelper {
	@SuppressWarnings("UnnecessaryReturnStatement")
	private TerraformBoatClientHelper() {
		return;
	}

	/**
	 * Registers {@linkplain ModelLayerLocation model layers} and
	 * {@linkplain BoatRenderer entity renderers} for all boats of given boat type.
	 * The provided identifier must match the identifier used to
	 * {@linkplain TerraformBoatItemHelper#registerBoatItem register the boat type}.
	 *
	 * <pre>{@code
	 *     TerraformBoatClientHelper.registerModelLayers(Identifier.fromNamespaceAndPath("examplemod", "mahogany"));
	 * }</pre>
	 *
	 * @param id the {@linkplain net.minecraft.resources.Identifier identifier} of the boat type.
	 */
	public static void registerModelLayers(Identifier id) {
		TerraformBoatClientHelperImpl.registerModelLayers(id);
	}
}
