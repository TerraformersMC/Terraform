package com.terraformersmc.terraform.tree.impl.merchant;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class TerraformSaplingTradeHelperImpl {
	@SuppressWarnings("UnnecessaryReturnStatement")
	private TerraformSaplingTradeHelperImpl() {
		return;
	}

	public static void registerWanderingTraderSaplingTrades(ItemLike... saplings) {
		TradeOfferHelper.registerWanderingTraderOffers(builder ->
				builder.addOffersToPool(TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
						Arrays.stream(saplings).map(SellSaplingFactory::new).collect(Collectors.toSet())));
	}

	private static class SellSaplingFactory implements VillagerTrades.ItemListing {
		private final ItemStack sapling;

		public SellSaplingFactory(ItemLike sapling) {
			this.sapling = new ItemStack(sapling);
		}

		@Override
		public @Nullable MerchantOffer getOffer(ServerLevel world, Entity entity, RandomSource random) {
			return new MerchantOffer(new ItemCost(Items.EMERALD, 5), this.sapling, 8, 1, 0.05f);
		}
	}
}
