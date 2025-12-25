package com.terraformersmc.terraform.tree.impl.merchant;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.item.trading.VillagerTrades;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.stream.Collectors;

import static net.minecraft.world.item.trading.VillagerTrades.resourceKey;

public final class TerraformSaplingTradeHelperImpl {
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_SPRUCE_SAPLING = resourceKey("wandering_trader/emerald_spruce_sapling");

	@SuppressWarnings("UnnecessaryReturnStatement")
	private TerraformSaplingTradeHelperImpl() {
		return;
	}

	public static void registerWanderingTraderSaplingTrades(ItemLike... saplings) {
/* TODO:  This feature is no longer useful, as trades are data.  Evaluate whether a datagen helper is useful.
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
*/
	}
}
