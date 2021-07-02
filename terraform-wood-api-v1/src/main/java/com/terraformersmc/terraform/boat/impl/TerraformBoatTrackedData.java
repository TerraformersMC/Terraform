package com.terraformersmc.terraform.boat.impl;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;

import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.PacketByteBuf;

public final class TerraformBoatTrackedData {
	private TerraformBoatTrackedData() {
		return;
	}

	public static final TrackedDataHandler<TerraformBoatType> HANDLER = new TrackedDataHandler<TerraformBoatType>() {
		public void write(PacketByteBuf buf, TerraformBoatType boat) {
			buf.writeIdentifier(TerraformBoatTypeRegistry.INSTANCE.getId(boat));
		}

		public TerraformBoatType read(PacketByteBuf buf) {
			return TerraformBoatTypeRegistry.INSTANCE.get(buf.readIdentifier());
		}

		public TerraformBoatType copy(TerraformBoatType boat) {
			return boat;
		}
	};

	protected static void register() {
		TrackedDataHandlerRegistry.register(HANDLER);
	}
}
