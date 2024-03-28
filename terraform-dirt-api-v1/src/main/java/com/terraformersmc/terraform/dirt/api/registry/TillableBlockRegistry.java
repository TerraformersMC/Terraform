package com.terraformersmc.terraform.dirt.api.registry;

import java.util.function.Consumer;
import java.util.function.Predicate;

import com.mojang.datafixers.util.Pair;

import com.terraformersmc.terraform.dirt.impl.registry.TillableBlockRegistryImpl;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;

/**
 * Allows the addition of custom tillable block mappings. You probably don't need to use this directly if you're using
 * {@link TerraformDirtRegistry}.
 */
public final class TillableBlockRegistry {
	private TillableBlockRegistry() {
		return;
	}

	/**
	 * Adds a custom tillable block mapping.
	 *
	 * Note that you don't need to call this yourself if you're already using {@link TerraformDirtRegistry}.
	 *
	 * @param block the block being tilled
	 * @param pair the interaction between the blocks
	 */
	public static void add(Block block, Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>> pair) {
		TillableBlockRegistryImpl.add(block, pair);
	}
	
	/**
	 * Adds a custom tillable block mapping.
	 *
	 * Note that you don't need to call this yourself if you're already using {@link TerraformDirtRegistry}.
	 *
	 * @param block the block being tilled
	 * @param state the block to be replaced with
	 */
	public static void add(Block block, BlockState state) {
		TillableBlockRegistryImpl.add(block, Pair.of(HoeItem::canTillFarmland, HoeItem.createTillAction(state)));
	}
}
