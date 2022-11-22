package com.terraformersmc.terraform.boat.impl;

import java.util.Optional;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;

import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.PacketByteBuf;

public final class TerraformBoatTrackedData {
	private TerraformBoatTrackedData() {
		return;
	}

	public static final TrackedDataHandler<Optional<TerraformBoatType>> HANDLER = TrackedDataHandler.ofOptional(TerraformBoatTrackedData::write, TerraformBoatTrackedData::read);

	private static void write(PacketByteBuf buf, TerraformBoatType boat) {
		buf.writeRegistryValue(TerraformBoatTypeRegistry.INSTANCE, boat);
	}

	private static TerraformBoatType read(PacketByteBuf buf) {
		return buf.readRegistryValue(TerraformBoatTypeRegistry.INSTANCE);
	}

	protected static void register() {
		TrackedDataHandlerRegistry.register(HANDLER);
	}
}
