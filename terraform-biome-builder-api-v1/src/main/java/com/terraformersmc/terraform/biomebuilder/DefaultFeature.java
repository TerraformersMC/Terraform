package com.terraformersmc.terraform.biomebuilder;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

import java.util.function.Consumer;

public enum DefaultFeature {
	LAND_CARVERS("land_carvers", DefaultBiomeFeatures::addLandCarvers),
	OCEAN_CARVERS("ocean_carvers", DefaultBiomeFeatures::addOceanCarvers),
	DEFAULT_UNDERGROUND_STRUCTURES("default_underground_structures", DefaultBiomeFeatures::addDefaultUndergroundStructures),
	OCEAN_STRUCTURES("ocean_structures", DefaultBiomeFeatures::addOceanStructures),
	BADLANDS_UNDERGROUND_STRUCTURES("badlands_underground_structures", DefaultBiomeFeatures::addBadlandsUndergroundStructures),
	LAKES("lakes", DefaultBiomeFeatures::addDefaultLakes),
	DESERT_LAKES("desert_lakes", DefaultBiomeFeatures::addDesertLakes),
	DUNGEONS("dungeons", DefaultBiomeFeatures::addDungeons),
	MINEABLES("mineables", DefaultBiomeFeatures::addMineables),
	ORES("ores", DefaultBiomeFeatures::addDefaultOres),
	EXTRA_GOLD("extra_gold", DefaultBiomeFeatures::addExtraGoldOre),
	EMERALD_ORE("emerald_ore", DefaultBiomeFeatures::addEmeraldOre),
	INFECTED_STONE("infected_stone", DefaultBiomeFeatures::addInfestedStone),
	DISKS("disks", DefaultBiomeFeatures::addDefaultDisks),
	CLAY("clay", DefaultBiomeFeatures::addClay),
	MOSSY_ROCKS("mossy_rocks", DefaultBiomeFeatures::addMossyRocks),
	LARGE_FERNS("large_ferns", DefaultBiomeFeatures::addLargeFerns),
	SWEET_BERRY_BUSHES("sweet_berry_bushes", DefaultBiomeFeatures::addSweetBerryBushes),
	SWEET_BERRY_BUSHES_SNOWY("sweet_berry_bushes_snowy", DefaultBiomeFeatures::addSweetBerryBushesSnowy),
	BAMBOO("bamboo", DefaultBiomeFeatures::addBamboo),
	BAMBOO_JUNGLE_TREES("bamboo jungle trees", DefaultBiomeFeatures::addBambooJungleTrees),
	TAIGA_TREES("taiga_trees", DefaultBiomeFeatures::addTaigaTrees),
	WATER_BIOME_OAK_TREES("water_biome_oak_trees", DefaultBiomeFeatures::addWaterBiomeOakTrees),
	BIRCH_TREES("birch_trees", DefaultBiomeFeatures::addBirchTrees),
	FOREST_TREES("forest_trees", DefaultBiomeFeatures::addForestTrees),
	TALL_BIRCH_TREES("tall_birch_trees", DefaultBiomeFeatures::addTallBirchTrees),
	SAVANNA_TREES("savannah_trees", DefaultBiomeFeatures::addSavannaTrees),
	EXTRA_SAVANNA_TREES("extra_savannah_trees", DefaultBiomeFeatures::addExtraSavannaTrees),
	MOUNTAIN_TREES("mountain_trees", DefaultBiomeFeatures::addMountainTrees),
	EXTRA_MOUNTAIN_TREES("extra_mountain_trees", DefaultBiomeFeatures::addExtraMountainTrees),
	JUNGLE_TREES("jungle_trees", DefaultBiomeFeatures::addJungleTrees),
	JUNGLE_EDGE_TREES("jungle_edge_trees", DefaultBiomeFeatures::addJungleEdgeTrees),
	BADLANDS_PLATEAU_TREES("badlands_plateau_trees", DefaultBiomeFeatures::addBadlandsPlateauTrees),
	SNOWY_SPRUCE_TREES("snowy_spruce_trees", DefaultBiomeFeatures::addSnowySpruceTrees),
	JUNGLE_GRASS("jungle_grass", DefaultBiomeFeatures::addJungleGrass),
	SAVANNA_TALL_GRASS("savanna_tall_grass", DefaultBiomeFeatures::addSavannaTallGrass),
	SHATTERED_SAVANNA_TALL_GRASS("shattered_savannah_tall_grass", DefaultBiomeFeatures::addShatteredSavannaGrass),
	SAVANNA_GRASS("savannah_grass", DefaultBiomeFeatures::addSavannaGrass),
	BADLANDS_GRASS("badlands_grass", DefaultBiomeFeatures::addBadlandsGrass),
	FOREST_FLOWERS("forrest_flowers", DefaultBiomeFeatures::addForestFlowers),
	FOREST_GRASS("forrest_grass", DefaultBiomeFeatures::addForestGrass),
	SWAMP_FEATURES("swamp_features", DefaultBiomeFeatures::addSwampFeatures),
	MUSHROOM_FIELDS_FEATURES("mushroom_fields_features", DefaultBiomeFeatures::addMushroomFieldsFeatures),
	PLAINS_FEATURES("plains_features", DefaultBiomeFeatures::addPlainsFeatures),
	DESERT_DEAD_BUSHES("desert_dead_bushes", DefaultBiomeFeatures::addDesertDeadBushes),
	GIANT_TAIGA_GRASS("giant_taiga_grass", DefaultBiomeFeatures::addGiantTaigaGrass),
	DEFAULT_FLOWERS("default_flowers", DefaultBiomeFeatures::addDefaultFlowers),
	EXTRA_DEFAULT_FLOWERS("extra_default_flowers", DefaultBiomeFeatures::addExtraDefaultFlowers),
	DEFAULT_GRASS("default_grass", DefaultBiomeFeatures::addDefaultGrass),
	TAIGA_GRASS("taiga_grass", DefaultBiomeFeatures::addTaigaGrass),
	PLAINS_TALL_GRASS("plains_tall_grass", DefaultBiomeFeatures::addPlainsTallGrass),
	DEFAULT_MUSHROOMS("default_mushrooms", DefaultBiomeFeatures::addDefaultMushrooms),
	DEFAULT_VEGETATION("default_vegetation", DefaultBiomeFeatures::addDefaultVegetation),
	BADLANDS_VEGETATION("badlands_vegetation", DefaultBiomeFeatures::addBadlandsVegetation),
	JUNGLE_VEGETATION("jungle_vegetation", DefaultBiomeFeatures::addJungleVegetation),
	DESERT_VEGETATION("desert_vegetation", DefaultBiomeFeatures::addDesertVegetation),
	SWAMP_VEGETATION("swamp_vegetation", DefaultBiomeFeatures::addSwampVegetation),
	DESERT_FEATURES("desert_features", DefaultBiomeFeatures::addDesertFeatures),
	FOSSILS("fossils", DefaultBiomeFeatures::addFossils),
	KELP("kelp", DefaultBiomeFeatures::addKelp),
	SEAGRASS_ON_STONE("seagrass_on_stone", DefaultBiomeFeatures::addSeagrassOnStone),
	LESS_KELP("less_kelp", DefaultBiomeFeatures::addLessKelp),
	SPRINGS("springs", DefaultBiomeFeatures::addSprings),
	ICEBERGS("icebergs", DefaultBiomeFeatures::addIcebergs),
	BLUE_ICE("blue_ice", DefaultBiomeFeatures::addBlueIce),
	FROZEN_TOP_LAYER("frozen_top_layer", DefaultBiomeFeatures::addFrozenTopLayer);

	private final String name;
	private final Consumer<GenerationSettings.Builder> function;

	DefaultFeature(String name, Consumer<GenerationSettings.Builder> function) {
		this.name = name;
		this.function = function;
	}

	public String getName() {
		return this.name;
	}

	public Consumer<GenerationSettings.Builder> getFunction() {
		return this.function;
	}

	public void add(GenerationSettings.Builder genBuilder) {
		this.function.accept(genBuilder);
	}
}
