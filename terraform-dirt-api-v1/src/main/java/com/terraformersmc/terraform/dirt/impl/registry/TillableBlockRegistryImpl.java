package com.terraformersmc.terraform.dirt.impl.registry;

import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

public final class TillableBlockRegistryImpl extends HoeItem {
	private TillableBlockRegistryImpl(ToolMaterial material, float attackDamage, float attackSpeed, Properties settings) {
		super(material, attackDamage, attackSpeed, settings);
		return;
	}

	public static void add(Block block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair) {
		TILLABLES.put(block, pair);
	}
}
