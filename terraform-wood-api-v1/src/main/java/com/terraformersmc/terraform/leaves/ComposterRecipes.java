package com.terraformersmc.terraform.leaves;

import com.terraformersmc.terraform.leaves.block.LeafPileBlock;

import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;

import java.util.Objects;

/**
 * @deprecated This class is deprecated in favor of using Fabric's CompostingChanceRegistry.
 *
 * <p>For example:<br />
 * <pre>{@code
 *     LeavesBlock leavesBlock = new LeavesBlock(...);
 *     BlockItem leavesItem = new BlockItem(leavesBlock, new Item.Settings());
 *     CompostingChanceRegistry.INSTANCE.add(leavesItem, chance);
 * }</pre>
 * </p>
 */
@Deprecated(since = "9.0.0", forRemoval = true)
public class ComposterRecipes {
	/**
	 * @deprecated Use {@code CompostingChanceRegistry.INSTANCE.add(item, chance) } instead.
	 *
	 * @param item A compostable item
	 * @param chance The compostable item's composter fill chance
	 */
	@Deprecated
	private static void registerCompostableItem(ItemConvertible item, float chance) {
		if (item.asItem() != Items.AIR) {
			ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(item.asItem(), chance);
		}
	}

	/**
	 * @deprecated Use {@code CompostingChanceRegistry.INSTANCE.add(item, chance) } instead.
	 *
	 * <p><br />
	 *     Blocks this method is capable of registering:
	 *     <li>Fern and Large Fern</li>
	 *     <li>Extenders of: FlowerBlock, LeafPileBlock, LeavesBlock, SaplingBlock, SeagrassBlock</li>
	 * </p>
	 *
	 * @param block A block with a compostable item
	 */
	@Deprecated
	public static void registerCompostableBlock(Block block) {
		Objects.requireNonNull(block);

		if (block instanceof LeavesBlock || block instanceof LeafPileBlock || block instanceof SaplingBlock || block instanceof SeagrassBlock) {
			registerCompostableItem(block, 0.3F);
		} else if (block.equals(Blocks.FERN) || block.equals(Blocks.LARGE_FERN) || block instanceof FlowerBlock) {
			registerCompostableItem(block, 0.65F);
		}
	}
}
