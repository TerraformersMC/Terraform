package com.terraformersmc.terraform.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.dimension.DimensionType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * A command that generates a map image of biome generation borders
 */
public class MapBiomesCommand {
	private static final Map<Biome, Integer> BIOME2COLOR = new HashMap<>();
	private static DecimalFormat numberFormat = new DecimalFormat("#.00");

	static {
		BIOME2COLOR.put(Biomes.NETHER_WASTES, 0xff7700);
		BIOME2COLOR.put(Biomes.WARPED_FOREST, 0x00e6b89);
		BIOME2COLOR.put(Biomes.CRIMSON_FOREST, 0xee0000);
		BIOME2COLOR.put(Biomes.SOUL_SAND_VALLEY, 0x45e3ff);
		BIOME2COLOR.put(Biomes.BASALT_DELTAS, 0x949494);
	}

	public static void register() {
		CommandRegistry.INSTANCE.register(false, dispatcher -> {
			LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("mapbiomes").requires(source ->
				source.hasPermissionLevel(2));

			builder.executes(context -> {
				execute(context.getSource());
				return 1;
			});

			dispatcher.register(builder);
		});
	}

	private static void execute(ServerCommandSource source) {
		//while this *can* be run in the overworld, it's gonna be pretty useless
		if (source.getWorld().getDimensionRegistryKey() != DimensionType.THE_NETHER_REGISTRY_KEY) {
			source.sendFeedback(new LiteralText("Please run this in the nether."), false);
			return;
		}

		//setup image
		Map<Biome, Integer> biomeCount = new HashMap<>();
		BufferedImage img = new BufferedImage(2048, 2048, BufferedImage.TYPE_INT_RGB);

		int progressUpdate = img.getHeight() / 8;

		for (int x = 0; x < img.getHeight(); x++) {
			for (int z = 0; z < img.getWidth(); z++) {
				Biome b = source.getWorld().getBiomeForNoiseGen(x * 4, 0, z * 4);
				Integer color = BIOME2COLOR.get(b);
				if (color == null) {
					color = 0xffffff;
				}

				//rather dumb way of adding the count per biome
				if (!biomeCount.containsKey(b)) {
					biomeCount.put(b, 0);
				} else {
					biomeCount.put(b, biomeCount.get(b) + 1);
				}

				//set the color
				img.setRGB(x, z, color);
			}

			//send a progress update to let people know the server isn't dying
			if (x % progressUpdate == 0) {
				source.sendFeedback(new TranslatableText(((double) x / img.getHeight()) * 100 + "% Done mapping"), true);
			}
		}

		source.sendFeedback(new LiteralText("Approximate biome-block counts within an 8192x8192 region"), true);
		//summate all of the biome counts
		int totalCount = biomeCount.values().stream().mapToInt(i -> i).sum();
		//TODO: sort by total count
		biomeCount.forEach((biome, integer) -> source.sendFeedback(new TranslatableText(biome.getTranslationKey()).append(": " + (integer * 16) + Formatting.GRAY + " (" + numberFormat.format(((double) integer / totalCount) * 100) + "%)"), true));

		//save the biome map
		Path p = Paths.get("biomemap.png");
		try {
			ImageIO.write(img, "png", p.toAbsolutePath().toFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
