package com.terraformersmc.terraform.boat.impl.item;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.impl.entity.TerraformBoatEntity;
import com.terraformersmc.terraform.boat.impl.entity.TerraformChestBoatEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

/**
 * An {@linkplain Item item} that spawns a {@linkplain TerraformBoatEntity boat entity} with a given {@linkplain TerraformBoatType Terraform boat type}.
 */
public class TerraformBoatItem extends Item {
	private static final Predicate<Entity> RIDERS = EntityPredicates.EXCEPT_SPECTATOR.and(Entity::canHit);

	private final Supplier<TerraformBoatType> boatSupplier;
	private final boolean chest;

	/**
	 * @param boatSupplier a {@linkplain Supplier supplier} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this item
	 */
	public TerraformBoatItem(Supplier<TerraformBoatType> boatSupplier, boolean chest, Item.Settings settings) {
		super(settings);

		this.boatSupplier = boatSupplier;
		this.chest = chest;
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);

		HitResult hitResult = Item.raycast(world, user, RaycastContext.FluidHandling.ANY);
		if (hitResult.getType() == HitResult.Type.MISS) {
			return TypedActionResult.pass(stack);
		}

		Vec3d rotationVec = user.getRotationVec(1f);
		List<Entity> riders = world.getOtherEntities(user, user.getBoundingBox().stretch(rotationVec.multiply(5d)).expand(1d), RIDERS);

		// Prevent collision with user
		if (!riders.isEmpty()) {
			Vec3d eyePos = user.getEyePos();
			for (Entity entity : riders) {
				Box box = entity.getBoundingBox().expand(entity.getTargetingMargin());
				if (box.contains(eyePos)) {
					return TypedActionResult.pass(stack);
				}
			}
		}

		// Spawn boat entity
		if (hitResult.getType() == HitResult.Type.BLOCK) {
			double x = hitResult.getPos().x;
			double y = hitResult.getPos().y;
			double z = hitResult.getPos().z;

			TerraformBoatType boatType = this.boatSupplier.get();
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

			boatEntity.setYaw(user.getYaw());

			if (!world.isSpaceEmpty(boatEntity, boatEntity.getBoundingBox().expand(-0.1d))) {
				return TypedActionResult.fail(stack);
			}
			
			if (!world.isClient()) {
				world.spawnEntity(boatEntity);
				world.emitGameEvent(user, GameEvent.ENTITY_PLACE, new BlockPos(hitResult.getPos()));

				if (!user.getAbilities().creativeMode) {
					stack.decrement(1);
				}
			}

			user.incrementStat(Stats.USED.getOrCreateStat(this));
			return TypedActionResult.success(stack, world.isClient());
		}

		return TypedActionResult.pass(stack);
	}
}
