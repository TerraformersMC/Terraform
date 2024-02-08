package com.terraformersmc.terraform.boat.impl;

import java.util.Optional;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;

import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public final class TerraformBoatTrackedData {
	private TerraformBoatTrackedData() {
		return;
	}

	public static final PacketCodec<RegistryByteBuf, Optional<TerraformBoatType>> PACKET_CODEC = PacketCodecs
		.registryValue(TerraformBoatTypeRegistry.INSTANCE.getKey())
		.collect(PacketCodecs::optional);

	public static final TrackedDataHandler<Optional<TerraformBoatType>> HANDLER = TrackedDataHandler.create(PACKET_CODEC);

	protected static void register() {
		TrackedDataHandlerRegistry.register(HANDLER);
	}
}
