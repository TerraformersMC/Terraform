package com.terraformersmc.terraform.dirt.api.registry;

import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import com.mojang.datafixers.util.Pair;

import com.terraformersmc.terraform.dirt.impl.registry.TillableBlockRegistryImpl;

/**
 * Allows the addition of custom tillable block mappings. You probably don't need to use this directly if you're using
 * {@link TerraformDirtRegistry}.
 */
@SuppressWarnings("unused")
public final class TillableBlockRegistry {
	@SuppressWarnings("UnnecessaryReturnStatement")
	private TillableBlockRegistry() {
		return;
	}

	/**
	 * Adds a custom tillable block mapping.
	 * <p/>
	 * Note that you don't need to call this yourself if you're already using {@link TerraformDirtRegistry}.
	 *
	 * @param block the block being tilled
	 * @param pair the interaction between the blocks
	 */
	public static void add(Block block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair) {
		TillableBlockRegistryImpl.add(block, pair);
	}
	
	/**
	 * Adds a custom tillable block mapping.
	 * <p/>
	 * Note that you don't need to call this yourself if you're already using {@link TerraformDirtRegistry}.
	 *
	 * @param block the block being tilled
	 * @param state the block to be replaced with
	 */
	public static void add(Block block, BlockState state) {
		TillableBlockRegistryImpl.add(block, Pair.of(HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(state)));
	}
}
