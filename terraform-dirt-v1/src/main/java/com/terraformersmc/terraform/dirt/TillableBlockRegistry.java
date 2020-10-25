package com.terraformersmc.terraform.dirt;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ToolMaterial;

public abstract class TillableBlockRegistry extends HoeItem {
	private TillableBlockRegistry(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
		super(material, attackDamage, attackSpeed, settings);
	}

	public static void add(Block block, BlockState state) {
		TILLED_BLOCKS.put(block, state);
	}
}
