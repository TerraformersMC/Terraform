package com.terraformersmc.terraform.boat.impl;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;

import net.minecraft.item.Item;

/**
 * A simple implementation of {@link TerraformBoatType}.
 */
public class TerraformBoatTypeImpl implements TerraformBoatType {
	private final Item item;
	private final Item chestItem;
	private final Item planks;

	public TerraformBoatTypeImpl(Item item, Item chestItem, Item planks) {
		this.item = item;
		this.chestItem = chestItem;
		this.planks = planks;
	}

	@Override
	public Item getItem() {
		return this.item;
	}

	@Override
	public Item getChestItem() {
		return this.chestItem;
	}

	@Override
	public Item getPlanks() {
		return this.planks;
	}
}
