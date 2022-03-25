package com.terraformersmc.terraform.boat.impl.entity;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.impl.TerraformBoatInitializer;
import com.terraformersmc.terraform.boat.impl.TerraformBoatTrackedData;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

/**
 * A {@linkplain BoatEntity boat entity} that stores a {@linkplain TerraformBoatType Terraform boat type}.
 */
public class TerraformBoatEntity extends BoatEntity implements TerraformBoatHolder {
	private static final TrackedData<TerraformBoatType> TERRAFORM_BOAT = DataTracker.registerData(TerraformBoatEntity.class, TerraformBoatTrackedData.HANDLER);

	public TerraformBoatEntity(EntityType<? extends TerraformBoatEntity> type, World world) {
		super(type, world);
	}

	public TerraformBoatEntity(World world) {
		this(TerraformBoatInitializer.BOAT, world);
	}

	public TerraformBoatEntity(World world, double x, double y, double z) {
		this(TerraformBoatInitializer.BOAT, world);

		this.setPosition(x, y, z);
		this.prevX = x;
		this.prevY = y;
		this.prevZ = z;
	}

	@Override
	public TerraformBoatType getTerraformBoat() {
		return this.dataTracker.get(TERRAFORM_BOAT);
	}

	@Override
	public void setTerraformBoat(TerraformBoatType boat) {
		this.dataTracker.set(TERRAFORM_BOAT, boat);
	}

	@Override
	public Item asItem() {
		return this.getTerraformBoat().getItem();
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
	public void setBoatType(BoatEntity.Type type) {
		return;
	}

	@Override
	public BoatEntity.Type getBoatType() {
		return BoatEntity.Type.OAK;
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(TERRAFORM_BOAT, null);
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
