package com.terraformersmc.terraform.biometag.api;

import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public final class TerraformBiomeTags {
	/**
	 * Contains biomes that should spawn slimes on the surface depending on the lunar phase.
	 */
	public static final TagKey<Biome> SURFACE_SLIME_SPAWNS = register("surface_slime_spawns");

	/**
	 * Contains biomes where bone meal used underwater can grow coral in addition to seagrass.
	 */
	public static final TagKey<Biome> WARM_UNDERWATER_FERTILIZATION = register("warm_underwater_fertilization");

	private TerraformBiomeTags() {
		return;
	}

	private static TagKey<Biome> register(String id) {
		return TagKey.of(Registry.BIOME_KEY, new Identifier("terraform", id));
	}
}
