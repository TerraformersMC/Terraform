package com.terraformersmc.terraform.wood.test.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.terraformersmc.terraform.boat.api.data.TerraformBoatData;
import com.terraformersmc.terraform.wood.test.TerraformWoodTest;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.entity.vehicle.boat.ChestRaft;
import net.minecraft.world.entity.vehicle.boat.Raft;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class SpawnBoatsCommand implements CommandRegistrationCallback {
	public static final SpawnBoatsCommand INSTANCE = new SpawnBoatsCommand();
	private static final Identifier ADVANCEMENT_ID = Identifier.withDefaultNamespace("husbandry/ride_a_boat_with_a_goat");

	private SpawnBoatsCommand() {
	}

	@Override
	public void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
		dispatcher.register(Commands.literal("terraform_spawn_boats").executes(SpawnBoatsCommand::execute));
	}

	private static int execute(CommandContext<CommandSourceStack> context) {
		CommandSourceStack source = context.getSource();

		ServerLevel level = source.getLevel();
		Vec3 pos = source.getPosition();

		TerraformBoatData boatData = TerraformBoatData.get(TerraformWoodTest.CUSTOM_BOATS_ID);

		// Revoke advancement
		ServerPlayer player = source.getPlayer();

		if (player != null) {
			AdvancementHolder advancement = source.getServer().getAdvancements().get(ADVANCEMENT_ID);

			if (advancement != null) {
				AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);

				if (progress.hasProgress()) {
					for (String criterion : progress.getCompletedCriteria()) {
						player.getAdvancements().revoke(advancement, criterion);
					}
				}
			}
		}

		// Spawn boats
		Boat boat = new Boat(boatData.boatEntityType(), level, () -> TerraformWoodTest.customBoatItem);
		boat.setPos(pos.x(), pos.y(), pos.z());
		level.addFreshEntity(boat);

		ChestBoat chestBoat = new ChestBoat(boatData.chestBoatEntityType(), level, () -> TerraformWoodTest.customChestBoatItem);
		chestBoat.setPos(pos.x() - 2, pos.y(), pos.z());
		level.addFreshEntity(chestBoat);

		Raft raft = new Raft(boatData.raftEntityType(), level, () -> TerraformWoodTest.customRaftItem);
		raft.setPos(pos.x() - 4, pos.y(), pos.z());
		level.addFreshEntity(raft);

		ChestRaft chestRaft = new ChestRaft(boatData.chestRaftEntityType(), level, () -> TerraformWoodTest.customChestRaftItem);
		chestRaft.setPos(pos.x() - 6, pos.y(), pos.z());
		level.addFreshEntity(chestRaft);

		// Spawn passengers
		addPassenger(level, new Goat(EntityType.GOAT, level), boat);
		addPassenger(level, new Shulker(EntityType.SHULKER, level), chestBoat);

		addPassenger(level, new Goat(EntityType.GOAT, level), raft);
		addPassenger(level, new Shulker(EntityType.SHULKER, level), chestRaft);

		return Command.SINGLE_SUCCESS;
	}

	private static void addPassenger(ServerLevel level, Mob passenger, Entity vehicle) {
		passenger.setNoAi(true);
		passenger.setSilent(true);

		level.addFreshEntity(passenger);
		passenger.startRiding(vehicle);
	}
}
