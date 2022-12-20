package com.terraformersmc.terraform.boat.impl;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;

import net.minecraft.item.ItemConvertible;

/**
 * A simple implementation of {@link TerraformBoatType}.
 */
public class TerraformBoatTypeImpl implements TerraformBoatType {
	private final boolean raft;
	private final ItemConvertible item;
	private final ItemConvertible chestItem;
	private final ItemConvertible planks;

	public TerraformBoatTypeImpl(boolean raft, ItemConvertible item, ItemConvertible chestItem, ItemConvertible planks) {
		this.raft = raft;
		this.item = item;
		this.chestItem = chestItem;
		this.planks = planks;
	}

	@Override
	public boolean isRaft() {
		return this.raft;
	}

	@Override
	public ItemConvertible getItem() {
		return this.item;
	}

	@Override
	public ItemConvertible getChestItem() {
		return this.chestItem;
	}

	@Override
	public ItemConvertible getPlanks() {
		return this.planks;
	}
}
