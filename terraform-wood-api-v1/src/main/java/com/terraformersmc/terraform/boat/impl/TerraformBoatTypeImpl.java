package com.terraformersmc.terraform.boat.impl;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;

import net.minecraft.item.Item;

/**
 * A simple implementation of {@link TerraformBoatType}.
 */
public class TerraformBoatTypeImpl implements TerraformBoatType {
	private final Item item;

	public TerraformBoatTypeImpl(Item item) {
		this.item = item;
	}

	@Override
	public Item getItem() {
		return this.item;
	}
}
