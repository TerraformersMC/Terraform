package com.terraformersmc.terraform.boat.impl.entity;

import java.util.Optional;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.impl.TerraformBoatInitializer;
import com.terraformersmc.terraform.boat.impl.TerraformBoatTrackedData;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;

/**
 * A {@linkplain ChestBoatEntity chest boat entity} that stores a {@linkplain TerraformBoatType Terraform boat type}.
 */
public class TerraformChestBoatEntity extends ChestBoatEntity implements TerraformBoatHolder {
	private static final TrackedData<Optional<TerraformBoatType>> TERRAFORM_BOAT = DataTracker.registerData(TerraformChestBoatEntity.class, TerraformBoatTrackedData.HANDLER);

	public TerraformChestBoatEntity(EntityType<? extends TerraformChestBoatEntity> type, World world) {
		super(type, world);
	}

	public TerraformChestBoatEntity(World world) {
		this(TerraformBoatInitializer.CHEST_BOAT, world);
	}

	public TerraformChestBoatEntity(World world, double x, double y, double z) {
		this(TerraformBoatInitializer.CHEST_BOAT, world);

		this.setPosition(x, y, z);
		this.prevX = x;
		this.prevY = y;
		this.prevZ = z;
	}

	@Override
	public TerraformBoatType getTerraformBoat() {
		return this.dataTracker.get(TERRAFORM_BOAT).orElse(null);
	}

	@Override
	public void setTerraformBoat(TerraformBoatType boat) {
		this.dataTracker.set(TERRAFORM_BOAT, Optional.of(boat));
	}

	@Override
	protected Text getDefaultName() {
		return EntityType.CHEST_BOAT.getName();
	}

	@Override
	public Item asItem() {
		return this.getTerraformBoat().getChestItem();
	}

	@Override
	public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
		return this.hasValidTerraformBoat() && super.shouldRender(cameraX, cameraY, cameraZ);
	}

	@Override
	public void tick() {
		if (this.hasValidTerraformBoat()) {
			super.tick();
		} else {
			this.discard();
		}
	}

	@Override
	public void method_47826(BoatEntity.Type type) {
		return;
	}

	@Override
	public BoatEntity.Type method_47827() {
		return this.getImpersonatedBoatType();
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(TERRAFORM_BOAT, Optional.empty());
	}

	// Serialization
	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.readTerraformBoatFromNbt(nbt);

		if (!this.hasValidTerraformBoat()) {
			this.discard();
		}
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		this.writeTerraformBoatToNbt(nbt);
	}
}
