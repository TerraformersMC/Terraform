package com.terraformersmc.terraform.boat.impl;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;

import net.minecraft.item.Item;

/**
 * A simple implementation of {@link TerraformBoatType}.
 */
public class TerraformBoatTypeImpl implements TerraformBoatType {
	private final Item item;
	private final Item chestItem;

	public TerraformBoatTypeImpl(Item item, Item chestItem) {
		this.item = item;
		this.chestItem = chestItem;
	}

	@Override
	public Item getItem() {
		return this.item;
	}

	@Override
	public Item getChestItem() {
		return this.chestItem;
	}
}
