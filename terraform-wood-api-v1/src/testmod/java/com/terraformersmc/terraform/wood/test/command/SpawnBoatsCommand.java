package com.terraformersmc.terraform.wood.test.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.impl.entity.TerraformBoatEntity;
import com.terraformersmc.terraform.wood.test.TerraformWoodTest;

import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public final class SpawnBoatsCommand {
	private static final Identifier ADVANCEMENT_ID = Identifier.ofVanilla("husbandry/ride_a_boat_with_a_goat");

	private SpawnBoatsCommand() {
		return;
	}

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(CommandManager.literal("terraform_spawn_boats").executes(SpawnBoatsCommand::execute));
	}

	private static int execute(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource();

		ServerWorld world = source.getWorld();
		Vec3d pos = source.getPosition();

		TerraformBoatType boatType = TerraformBoatTypeRegistry.INSTANCE.getOrThrow(TerraformWoodTest.CUSTOM_BOAT_KEY);
		TerraformBoatType raftType = TerraformBoatTypeRegistry.INSTANCE.getOrThrow(TerraformWoodTest.CUSTOM_RAFT_KEY);

		// Revoke advancement
		ServerPlayerEntity player = source.getPlayer();

		if (player != null) {
			AdvancementEntry advancement = source.getServer().getAdvancementLoader().get(ADVANCEMENT_ID);

			if (advancement != null) {
				AdvancementProgress progress = player.getAdvancementTracker().getProgress(advancement);

				if (progress.isAnyObtained()) {
					for (String criterion : progress.getObtainedCriteria()) {
						player.getAdvancementTracker().revokeCriterion(advancement, criterion);
					}
				}
			}
		}

		// Spawn boats
		TerraformBoatEntity boat = new TerraformBoatEntity(world, pos.getX(), pos.getY(), pos.getZ());
		boat.setTerraformBoat(boatType);
		world.spawnEntity(boat);

		TerraformBoatEntity chestBoat = new TerraformBoatEntity(world, pos.getX() - 2, pos.getY(), pos.getZ());
		chestBoat.setTerraformBoat(boatType);
		world.spawnEntity(chestBoat);

		TerraformBoatEntity raft = new TerraformBoatEntity(world, pos.getX() - 4, pos.getY(), pos.getZ());
		raft.setTerraformBoat(raftType);
		world.spawnEntity(raft);

		TerraformBoatEntity chestRaft = new TerraformBoatEntity(world, pos.getX() - 6, pos.getY(), pos.getZ());
		chestRaft.setTerraformBoat(raftType);
		world.spawnEntity(chestRaft);

		// Spawn passengers
		addPassenger(world, new GoatEntity(EntityType.GOAT, world), boat);
		addPassenger(world, new ShulkerEntity(EntityType.SHULKER, world), chestBoat);

		addPassenger(world, new GoatEntity(EntityType.GOAT, world), raft);
		addPassenger(world, new ShulkerEntity(EntityType.SHULKER, world), chestRaft);

		return Command.SINGLE_SUCCESS;
	}

	private static void addPassenger(ServerWorld world, MobEntity passenger, Entity vehicle) {
		passenger.setAiDisabled(true);
		passenger.setSilent(true);

		world.spawnEntity(passenger);
		passenger.startRiding(vehicle);
	}
}
