package com.terraformersmc.terraform.tree.api.merchant;

import com.terraformersmc.terraform.tree.impl.merchant.TerraformSaplingTradeHelperImpl;
import net.minecraft.world.level.ItemLike;

/**
 * A helper class for merchant trades regarding saplings.
 */
@SuppressWarnings("unused")
public final class TerraformSaplingTradeHelper {
	@SuppressWarnings("UnnecessaryReturnStatement")
	private TerraformSaplingTradeHelper() {
		return;
	}

	/**
	 * Registers a trade for wandering traders that sells saplings for 5 emeralds, similar to vanilla saplings.
	 */
	public static void registerWanderingTraderSaplingTrades(ItemLike... saplings) {
		TerraformSaplingTradeHelperImpl.registerWanderingTraderSaplingTrades(saplings);
	}
}
