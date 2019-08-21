package com.terraformersmc.terraform.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public abstract class TerraformBoatEntity extends BoatEntity {
	private static final TrackedData<String> EXTENDED_BOAT_TYPE = DataTracker.registerData(TerraformBoatEntity.class, TrackedDataHandlerRegistry.STRING);

	public TerraformBoatEntity(EntityType<? extends TerraformBoatEntity> type, World world) {
		super(type, world);
	}

	/*public TerraformBoatEntity(World world, double x, double y, double z) {
		this(getDefaultEntityType(), world);
		this.setPosition(x, y, z);
		this.setVelocity(Vec3d.ZERO);
		this.prevX = x;
		this.prevY = y;
		this.prevZ = z;
	}*/

	/*public static TerraformBoatEntity create(EntityType<? extends TerraformBoatEntity> type, World world) {
		return new TerraformBoatEntity(type, world);
	}*/

	protected abstract BoatTypeProvider getTypeProvider();

	@Override
	protected void initDataTracker() {
		super.initDataTracker();

		this.dataTracker.startTracking(EXTENDED_BOAT_TYPE, "");
	}

	@Override
	public Item asItem() {
		return getTypeProvider().asBoat(getExtendedBoatType());
	}

	public Item asPlanks() {
		return getTypeProvider().asPlanks(getExtendedBoatType());
	}

	public Identifier getBoatSkin() {
		return getTypeProvider().getSkin(getExtendedBoatType());
	}

	@Override
	protected void writeCustomDataToTag(CompoundTag tag) {
		tag.putString("Type", this.getExtendedBoatType());
	}

	@Override
	protected void readCustomDataFromTag(CompoundTag tag) {
		this.setExtendedBoatType(tag.getString("Type"));
	}

	private boolean isOnLand() {
		// super hackish way of evaluating the condition (this.location == BoatEntity.Location.ON_LAND)

		return getPaddleSoundEvent() == SoundEvents.ENTITY_BOAT_PADDLE_LAND;
	}

	@Override
	protected void fall(double double_1, boolean boolean_1, BlockState state, BlockPos pos) {

		float savedFallDistance = this.fallDistance;

		// Run other logic, including setting the private field fallVelocity
		super.fall(double_1, false, state, pos);

		if(!this.hasVehicle() && boolean_1) {
			this.fallDistance = savedFallDistance;

			if (this.fallDistance > 3.0F) {
				if (!isOnLand()) {
					this.fallDistance = 0.0F;
					return;
				}

				this.handleFallDamage(this.fallDistance, 1.0F);
				if (!this.world.isClient && !this.removed) {
					this.remove();
					if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
						for(int i = 0; i < 3; i++) {
							this.dropItem(this.asPlanks());
						}

						for(int i = 0; i < 2; i++) {
							this.dropItem(Items.STICK);
						}
					}
				}
			}

			this.fallDistance = 0.0F;
		}
	}

	@Override
	public void setBoatType(BoatEntity.Type type) {
		throw new UnsupportedOperationException("Tried to set the boat type of a Terraform boat");
	}

	@Override
	public BoatEntity.Type getBoatType() {
		return getTypeProvider().getVanillaType(getExtendedBoatType());
	}

	public void setExtendedBoatType(String type) {
		this.dataTracker.set(EXTENDED_BOAT_TYPE, type);
	}

	public String getExtendedBoatType() {
		return this.dataTracker.get(EXTENDED_BOAT_TYPE);
	}
}
