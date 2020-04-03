package com.terraformersmc.terraform.util;

import com.terraformersmc.terraform.block.ExtendedLeavesBlock;
import com.terraformersmc.terraform.block.LeafPileBlock;
import com.terraformersmc.terraform.block.TallCattailBlock;

import net.minecraft.block.Block;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.FernBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SeagrassBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;

public class RecipeUtil {
	private static void registerCompostableItem(ItemConvertible item, float chance) {
		if (item.asItem() != Items.AIR) {
			ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(item.asItem(), chance);
		}
	}

	public static void registerCompostableBlock(Block block) {
		if (block instanceof ExtendedLeavesBlock || block instanceof LeavesBlock || block instanceof LeafPileBlock || block instanceof SaplingBlock || block instanceof SeagrassBlock) {
			registerCompostableItem(block, 0.3F);
		} else if (block instanceof TallCattailBlock) {
			registerCompostableItem(block, 0.5F);
		} else if (block instanceof FernBlock || block instanceof FlowerBlock) {
			registerCompostableItem(block, 0.65F);
		}
	}
}
