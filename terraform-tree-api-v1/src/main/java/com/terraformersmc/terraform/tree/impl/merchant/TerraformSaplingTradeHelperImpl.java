package com.terraformersmc.terraform.tree.impl.merchant;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.ItemLike;

import static net.minecraft.world.item.trading.VillagerTrades.resourceKey;

/**
 * This class is no longer useful, as trades must now be registered via json data.
 */
@Deprecated(since = "17.0.0-alpha.1", forRemoval = true)
public final class TerraformSaplingTradeHelperImpl {
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_SPRUCE_SAPLING = resourceKey("wandering_trader/emerald_spruce_sapling");

	@SuppressWarnings("UnnecessaryReturnStatement")
	private TerraformSaplingTradeHelperImpl() {
		return;
	}

	/**
	 * This method is no longer useful, as trades must now be registered via json data.
	 * To raise visibility, an exception will be thrown if this method is called.
	 * <p/>
	 * This should instead be achieved via datagen, with a {@linkplain KeyTagProvider}<{@linkplain VillagerTrade}>.
	 * The provider from Traverse for 26.1 is provided below as an example:
	 *
	 * <pre>{@code
	 * @NullMarked
	 * public class TraverseVillagerTradeKeyTagProvider extends KeyTagProvider<VillagerTrade> {
	 *     protected TraverseVillagerTradeKeyTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
	 *         super(output, Registries.VILLAGER_TRADE, lookupProvider);
	 *     }
	 *
	 *     @Override
	 *     public void addTags(HolderLookup.Provider registries) {
	 *         this.tag(VillagerTradeTags.WANDERING_TRADER_COMMON)
	 *             .add(TraverseVillagerTrades.WANDERING_TRADER_EMERALD_FIR_LOG)
	 *             .add(TraverseVillagerTrades.WANDERING_TRADER_EMERALD_FIR_SAPLING)
	 *             .add(TraverseVillagerTrades.WANDERING_TRADER_EMERALD_BROWN_AUTUMNAL_SAPLING)
	 *             .add(TraverseVillagerTrades.WANDERING_TRADER_EMERALD_ORANGE_AUTUMNAL_SAPLING)
	 *             .add(TraverseVillagerTrades.WANDERING_TRADER_EMERALD_RED_AUTUMNAL_SAPLING)
	 *             .add(TraverseVillagerTrades.WANDERING_TRADER_EMERALD_YELLOW_AUTUMNAL_SAPLING);
	 *     }
	 *
	 *     @Override
	 *     public String getName() {
	 *         return "Traverse Villager Trade Tags";
	 *     }
	 * }
	 * }</pre>
	 *
	 * @param saplings ignored
	 */
	@Deprecated(since = "17.0.0-alpha.1", forRemoval = true)
	public static void registerWanderingTraderSaplingTrades(ItemLike... saplings) {
		throw new UnsupportedOperationException("registerWanderingTraderSaplingTrades has been removed from the API");
	}
}
