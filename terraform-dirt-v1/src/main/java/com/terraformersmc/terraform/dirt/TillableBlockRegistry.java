package com.terraformersmc.terraform.dirt;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ToolMaterial;

/**
 * Allows the addition of custom tillable block mappings. You probably don't need to use this directly if you're using
 * {@link TerraformDirtRegistry}.
 */
public abstract class TillableBlockRegistry extends HoeItem {
	private TillableBlockRegistry(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
		super(material, attackDamage, attackSpeed, settings);
	}

	/**
	 * Adds a custom tillable block mapping.
	 *
	 * Note that you don't need to call this yourself if you're already using {@link TerraformDirtRegistry}.
	 *
	 * @param block the block being tilled
	 * @param state the state to replace it with
	 */
	public static void add(Block block, BlockState state) {
		TILLED_BLOCKS.put(block, state);
	}
}
