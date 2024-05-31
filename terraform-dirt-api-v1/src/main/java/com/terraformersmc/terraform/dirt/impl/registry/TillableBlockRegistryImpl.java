package com.terraformersmc.terraform.dirt.impl.registry;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;

import java.util.function.Consumer;
import java.util.function.Predicate;

public final class TillableBlockRegistryImpl extends HoeItem {
	private TillableBlockRegistryImpl(ToolMaterial material, Settings settings) {
		super(material, settings);
		return;
	}

	public static void add(Block block, Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>> pair) {
		TILLING_ACTIONS.put(block, pair);
	}
}
