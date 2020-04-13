package com.terraformersmc.terraform.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ToolMaterial;

public abstract class HoeUtil extends HoeItem {
	private HoeUtil(ToolMaterial material, float attackSpeed, Settings settings) {
		super(material, attackSpeed, settings);
	}

	public static void putTillable(Block block, BlockState state) {
		TILLED_BLOCKS.put(block, state);
	}
}
