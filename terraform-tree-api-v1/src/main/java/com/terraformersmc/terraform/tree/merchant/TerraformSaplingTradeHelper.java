package com.terraformersmc.terraform.tree.merchant;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TradedItem;

/**
 * A helper class for merchant trades regarding saplings.
 */
public final class TerraformSaplingTradeHelper {
	private TerraformSaplingTradeHelper() {}

	/**
	 * Registers a trade for wandering traders that sells saplings for 5 emeralds, similar to vanilla saplings.
	 */
	public static void registerWanderingTraderSaplingTrades(ItemConvertible... saplings) {
		TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
			for (ItemConvertible sapling : saplings) {
				factories.add(new SellSaplingFactory(sapling));
			}
		});
	}

	private static class SellSaplingFactory implements TradeOffers.Factory {
		private final ItemStack sapling;

		public SellSaplingFactory(ItemConvertible sapling) {
			this.sapling = new ItemStack(sapling);
		}

		@Override
		public TradeOffer create(Entity entity, Random random) {
			return new TradeOffer(new TradedItem(Items.EMERALD, 5), this.sapling, 8, 1, 0.05f);
		}
	}
}
