package com.terraformersmc.terraform.boat.impl.item;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.impl.entity.TerraformBoatEntity;
import com.terraformersmc.terraform.boat.impl.entity.TerraformChestBoatEntity;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

/**
 * A {@linkplain DispenserBehavior dispenser behavior} that spawns a {@linkplain TerraformBoatEntity boat entity} with a given {@linkplain TerraformBoatType Terraform boat type}.
 */
public class TerraformBoatDispenserBehavior extends ItemDispenserBehavior {
	private static final DispenserBehavior FALLBACK_BEHAVIOR = new ItemDispenserBehavior();
	private static final float OFFSET_MULTIPLIER = 1.125F;

	private final RegistryKey<TerraformBoatType> boatKey;
	private final boolean chest;

	/**
	 * @param boatKey a {@linkplain RegistryKey registry key} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this dispenser behavior
	 * @param chest whether the boat contains a chest
	 */
	public TerraformBoatDispenserBehavior(RegistryKey<TerraformBoatType> boatKey, boolean chest) {
		this.boatKey = boatKey;
		this.chest = chest;
	}

	@Override
	public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
		Direction facing = pointer.getBlockState().get(DispenserBlock.FACING);

		double x = pointer.getX() + facing.getOffsetX() * OFFSET_MULTIPLIER;
		double y = pointer.getY() + facing.getOffsetY() * OFFSET_MULTIPLIER;
		double z = pointer.getZ() + facing.getOffsetZ() * OFFSET_MULTIPLIER;

		World world = pointer.getWorld();
		BlockPos pos = pointer.getPos().offset(facing);

		if (world.getFluidState(pos).isIn(FluidTags.WATER)) {
			y += 1;
		} else if (!world.getBlockState(pos).isAir() || !world.getFluidState(pos.down()).isIn(FluidTags.WATER)) {
			return FALLBACK_BEHAVIOR.dispense(pointer, stack);
		}

		TerraformBoatType boatType = TerraformBoatTypeRegistry.INSTANCE.getOrThrow(this.boatKey);
		BoatEntity boatEntity;

		if (this.chest) {
			TerraformChestBoatEntity chestBoat = new TerraformChestBoatEntity(world, x, y, z);
			chestBoat.setTerraformBoat(boatType);
			boatEntity = chestBoat;
		} else {
			TerraformBoatEntity boat = new TerraformBoatEntity(world, x, y, z);
			boat.setTerraformBoat(boatType);
			boatEntity = boat;
		}

		boatEntity.setYaw(facing.asRotation());

		world.spawnEntity(boatEntity);

		stack.decrement(1);
		return stack;
	}
}
