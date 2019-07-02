package io.github.terraformersmc.terraform.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.ChatFormat;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.chat.*;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Objects;

/**
 * Command to locate the nearest biome to the player that executed it
 *
 * Thanks to Dinolek for porting this https://github.com/Dinolek/LocateBiome
 * Based off https://github.com/Glitchfiend/BiomesOPlenty/blob/4977b0100ca55f96de50337f46ed673512cf503a/src/main/java/biomesoplenty/common/util/biome/BiomeUtils.java
 */
public class FindBiomeCommand {

	private static int timeout = 120_000;

	public static void register() {
		CommandRegistry.INSTANCE.register(false, dispatcher -> {
			LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("findbiome").requires(source ->
				source.hasPermissionLevel(2));
			Registry.BIOME.stream().forEach(biome -> builder.then(CommandManager.literal(Objects.requireNonNull(Registry.BIOME.getId(biome)).toString())
				.executes(context -> execute(context.getSource(), biome))));
			dispatcher.register(builder);
		});
	}

	private static int execute(ServerCommandSource source, Biome biome) {
		new Thread(() -> {
			BlockPos executorPos = new BlockPos(source.getPosition());
			BlockPos biomePos = null;
			TranslatableComponent biomeName = new TranslatableComponent(biome.getTranslationKey());
			try {
				biomePos = spiralOutwardsLookingForBiome(source, source.getWorld(), biome, executorPos.getX(), executorPos.getZ());
			} catch (CommandSyntaxException e) {
				e.printStackTrace();
			}

			if (biomePos == null) {
				source.sendFeedback(new TranslatableComponent(source.getMinecraftServer() instanceof DedicatedServer ? "optimizeWorld.stage.failed" : "commands.terraform.findbiome.fail",
					biomeName, timeout / 1000).applyFormat(ChatFormat.RED), true);
				return;
			}
			BlockPos finalBiomePos = biomePos;
			source.getMinecraftServer().execute(() -> {
				int distance = MathHelper.floor(getDistance(executorPos.getX(), executorPos.getZ(), finalBiomePos.getX(), finalBiomePos.getZ()));
				Component coordinates = Components.bracketed(new TranslatableComponent("chat.coordinates", finalBiomePos.getX(), "~",
					finalBiomePos.getZ())).setStyle(new Style().setColor(ChatFormat.GREEN)
					.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + finalBiomePos.getX() + " ~ " + finalBiomePos.getZ()))
					.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("chat.coordinates.tooltip"))));
				source.sendFeedback(new TranslatableComponent("commands.locate.success", biomeName, coordinates, distance), true);
			});
		}).start();
		return 0;
	}

	private static BlockPos spiralOutwardsLookingForBiome(ServerCommandSource source, World world, Biome biomeToFind, double startX, double startZ) throws CommandSyntaxException {
		double a = 16 / Math.sqrt(Math.PI);
		double b = 2 * Math.sqrt(Math.PI);
		double x, z;
		double dist = 0;
		long start = System.currentTimeMillis();
		BlockPos.PooledMutable pos = BlockPos.PooledMutable.get();
		int previous = 0;
		int i = 0;
		for (int n = 0; dist < Integer.MAX_VALUE; ++n) {
			if ((System.currentTimeMillis() - start) > timeout)
				return null;
			double rootN = Math.sqrt(n);
			dist = a * rootN;
			x = startX + (dist * Math.sin(b * rootN));
			z = startZ + (dist * Math.cos(b * rootN));
			pos.set(x, 0, z);
			if (previous == 3)
				previous = 0;
			String dots = (previous == 0 ? "." : previous == 1 ? ".." : "...");
			if (source.getEntity() instanceof PlayerEntity && !(source.getMinecraftServer() instanceof DedicatedServer))
				source.getPlayer().sendChatMessage(new TranslatableComponent("commands.terraform.findbiome.scanning", dots), ChatMessageType.GAME_INFO);
			if (i == 9216) {
				previous++;
				i = 0;
			}
			i++;
			if (world.getBiome(pos).equals(biomeToFind)) {
				pos.close();
				if (source.getEntity() instanceof PlayerEntity && !(source.getMinecraftServer() instanceof DedicatedServer))
					source.getPlayer().sendChatMessage(new TranslatableComponent("commands.terraform.findbiome.found", new TranslatableComponent(biomeToFind.getTranslationKey()), (System.currentTimeMillis() - start) / 1000), ChatMessageType.GAME_INFO);
				return new BlockPos((int) x, 0, (int) z);
			}
		}
		return null;
	}

	private static double getDistance(int posX, int posZ, int biomeX, int biomeZ) {
		return MathHelper.sqrt(Math.pow(biomeX - posX, 2) + Math.pow(biomeZ - posZ, 2));
	}

}