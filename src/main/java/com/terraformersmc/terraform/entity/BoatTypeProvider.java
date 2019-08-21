package com.terraformersmc.terraform.entity;

import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public interface BoatTypeProvider {
	Item asBoat(String name);
	Item asPlanks(String name);

	Identifier getSkin(String name);

	default BoatEntity.Type getVanillaType(String name) {
		return BoatEntity.Type.OAK;
	}
}
