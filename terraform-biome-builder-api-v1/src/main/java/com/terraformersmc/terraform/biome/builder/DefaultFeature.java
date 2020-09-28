package com.terraformersmc.terraform.biome.builder;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

public enum DefaultFeature {
	LAND_CARVERS("land_carvers"),
	OCEAN_CARVERS("ocean_carvers"),
	DEFAULT_UNDERGROUND_STRUCTURES("default_underground_structures"),
	OCEAN_STRUCTURES("ocean_structures"),
	BADLANDS_UNDERGROUND_STRUCTURES("badlands_underground_structures"),
	LAKES("lakes"),
	DESERT_LAKES("desert_lakes"),
	DUNGEONS("dungeons"),
	MINEABLES("mineables"),
	ORES("ores"),
	EXTRA_GOLD("extra_gold"),
	EMERALD_ORE("emerald_ore"),
	INFECTED_STONE("infected_stone"),
	DISKS("disks"),
	CLAY("clay"),
	MOSSY_ROCKS("mossy_rocks"),
	LARGE_FERNS("large_ferns"),
	SWEET_BERRY_BUSHES("sweet_berry_bushes"),
	SWEET_BERRY_BUSHES_SNOWY("sweet_berry_bushes_snowy"),
	BAMBOO("bamboo"),
	BAMBOO_JUNGLE_TREES("bamboo jungle trees"),
	TAIGA_TREES("taiga_trees"),
	WATER_BIOME_OAK_TREES("water_biome_oak_trees"),
	BIRCH_TREES("birch_trees"),
	FOREST_TREES("forest_trees"),
	TALL_BIRCH_TREES("tall_birch_trees"),
	SAVANNA_TREES("savannah_trees"),
	EXTRA_SAVANNA_TREES("extra_savannah_trees"),
	MOUNTAIN_TREES("mountain_trees"),
	EXTRA_MOUNTAIN_TREES("extra_mountain_trees"),
	JUNGLE_TREES("jungle_trees"),
	JUNGLE_EDGE_TREES("jungle_edge_trees"),
	BADLANDS_PLATEAU_TREES("badlands_plateau_trees"),
	SNOWY_SPRUCE_TREES("snowy_spruce_trees"),
	JUNGLE_GRASS("jungle_grass"),
	SAVANNA_TALL_GRASS("savanna_tall_grass"),
	SHATTERED_SAVANNA_TALL_GRASS("shattered_savannah_tall_grass"),
	SAVANNA_GRASS("savannah_grass"),
	BADLANDS_GRASS("badlands_grass"),
	FOREST_FLOWERS("forrest_flowers"),
	FOREST_GRASS("forrest_grass"),
	SWAMP_FEATURES("swamp_features"),
	MUSHROOM_FIELDS_FEATURES("mushroom_fields_features"),
	PLAINS_FEATURES("plains_features"),
	DESERT_DEAD_BUSHES("desert_dead_bushes"),
	GIANT_TAIGA_GRASS("giant_taiga_grass"),
	DEFAULT_FLOWERS("default_flowers"),
	EXTRA_DEFAULT_FLOWERS("extra_default_flowers"),
	DEFAULT_GRASS("default_grass"),
	TAIGA_GRASS("taiga_grass"),
	PLAINS_TALL_GRASS("plains_tall_grass"),
	DEFAULT_MUSHROOMS("default_mushrooms"),
	DEFAULT_VEGETATION("default_vegetation"),
	BADLANDS_VEGETATION("badlands_vegetation"),
	JUNGLE_VEGETATION("jungle_vegetation"),
	DESERT_VEGETATION("desert_vegetation"),
	SWAMP_VEGETATION("swamp_vegetation"),
	DESERT_FEATURES("desert_features"),
	FOSSILS("fossils"),
	KELP("kelp"),
	SEAGRASS_ON_STONE("seagrass_on_stone"),
	LESS_KELP("less_kelp"),
	SPRINGS("springs"),
	ICEBERGS("icebergs"),
	BLUE_ICE("blue_ice"),
	FROZEN_TOP_LAYER("frozen_top_layer");

	private final String name;

	DefaultFeature(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void add(GenerationSettings.Builder genBuilder) {
		switch (this) {
			case LAND_CARVERS:
				DefaultBiomeFeatures.addLandCarvers(genBuilder);
				break;
			case OCEAN_CARVERS:
				DefaultBiomeFeatures.addOceanCarvers(genBuilder);
				break;
			case DEFAULT_UNDERGROUND_STRUCTURES:
				DefaultBiomeFeatures.addDefaultUndergroundStructures(genBuilder);
				break;
			case OCEAN_STRUCTURES:
				DefaultBiomeFeatures.addOceanStructures(genBuilder);
				break;
			case BADLANDS_UNDERGROUND_STRUCTURES:
				DefaultBiomeFeatures.addBadlandsUndergroundStructures(genBuilder);
				break;
			case LAKES:
				DefaultBiomeFeatures.addDefaultLakes(genBuilder);
				break;
			case DESERT_LAKES:
				DefaultBiomeFeatures.addDesertLakes(genBuilder);
				break;
			case DUNGEONS:
				DefaultBiomeFeatures.addDungeons(genBuilder);
				break;
			case MINEABLES:
				DefaultBiomeFeatures.addMineables(genBuilder);
				break;
			case ORES:
				DefaultBiomeFeatures.addDefaultOres(genBuilder);
				break;
			case EXTRA_GOLD:
				DefaultBiomeFeatures.addExtraGoldOre(genBuilder);
				break;
			case EMERALD_ORE:
				DefaultBiomeFeatures.addEmeraldOre(genBuilder);
				break;
			case INFECTED_STONE:
				DefaultBiomeFeatures.addInfestedStone(genBuilder);
				break;
			case DISKS:
				DefaultBiomeFeatures.addDefaultDisks(genBuilder);
				break;
			case CLAY:
				DefaultBiomeFeatures.addClay(genBuilder);
				break;
			case MOSSY_ROCKS:
				DefaultBiomeFeatures.addMossyRocks(genBuilder);
				break;
			case LARGE_FERNS:
				DefaultBiomeFeatures.addLargeFerns(genBuilder);
				break;
			case SWEET_BERRY_BUSHES:
				DefaultBiomeFeatures.addSweetBerryBushes(genBuilder);
				break;
			case SWEET_BERRY_BUSHES_SNOWY:
				DefaultBiomeFeatures.addSweetBerryBushesSnowy(genBuilder);
				break;
			case BAMBOO:
				DefaultBiomeFeatures.addBamboo(genBuilder);
				break;
			case BAMBOO_JUNGLE_TREES:
				DefaultBiomeFeatures.addBambooJungleTrees(genBuilder);
				break;
			case TAIGA_TREES:
				DefaultBiomeFeatures.addTaigaTrees(genBuilder);
				break;
			case WATER_BIOME_OAK_TREES:
				DefaultBiomeFeatures.addWaterBiomeOakTrees(genBuilder);
				break;
			case BIRCH_TREES:
				DefaultBiomeFeatures.addBirchTrees(genBuilder);
				break;
			case FOREST_TREES:
				DefaultBiomeFeatures.addForestTrees(genBuilder);
				break;
			case TALL_BIRCH_TREES:
				DefaultBiomeFeatures.addTallBirchTrees(genBuilder);
				break;
			case SAVANNA_TREES:
				DefaultBiomeFeatures.addSavannaTrees(genBuilder);
				break;
			case EXTRA_SAVANNA_TREES:
				DefaultBiomeFeatures.addExtraSavannaTrees(genBuilder);
				break;
			case MOUNTAIN_TREES:
				DefaultBiomeFeatures.addMountainTrees(genBuilder);
				break;
			case EXTRA_MOUNTAIN_TREES:
				DefaultBiomeFeatures.addExtraMountainTrees(genBuilder);
				break;
			case JUNGLE_TREES:
				DefaultBiomeFeatures.addJungleTrees(genBuilder);
				break;
			case JUNGLE_EDGE_TREES:
				DefaultBiomeFeatures.addJungleEdgeTrees(genBuilder);
				break;
			case BADLANDS_PLATEAU_TREES:
				DefaultBiomeFeatures.addBadlandsPlateauTrees(genBuilder);
				break;
			case SNOWY_SPRUCE_TREES:
				DefaultBiomeFeatures.addSnowySpruceTrees(genBuilder);
				break;
			case JUNGLE_GRASS:
				DefaultBiomeFeatures.addJungleGrass(genBuilder);
				break;
			case SAVANNA_TALL_GRASS:
				DefaultBiomeFeatures.addSavannaTallGrass(genBuilder);
				break;
			case SHATTERED_SAVANNA_TALL_GRASS:
				DefaultBiomeFeatures.addShatteredSavannaGrass(genBuilder);
				break;
			case SAVANNA_GRASS:
				DefaultBiomeFeatures.addSavannaGrass(genBuilder);
				break;
			case BADLANDS_GRASS:
				DefaultBiomeFeatures.addBadlandsGrass(genBuilder);
				break;
			case FOREST_FLOWERS:
				DefaultBiomeFeatures.addForestFlowers(genBuilder);
				break;
			case FOREST_GRASS:
				DefaultBiomeFeatures.addForestGrass(genBuilder);
				break;
			case SWAMP_FEATURES:
				DefaultBiomeFeatures.addSwampFeatures(genBuilder);
				break;
			case MUSHROOM_FIELDS_FEATURES:
				DefaultBiomeFeatures.addMushroomFieldsFeatures(genBuilder);
				break;
			case PLAINS_FEATURES:
				DefaultBiomeFeatures.addPlainsFeatures(genBuilder);
				break;
			case DESERT_DEAD_BUSHES:
				DefaultBiomeFeatures.addDesertDeadBushes(genBuilder);
				break;
			case GIANT_TAIGA_GRASS:
				DefaultBiomeFeatures.addGiantTaigaGrass(genBuilder);
				break;
			case DEFAULT_FLOWERS:
				DefaultBiomeFeatures.addDefaultFlowers(genBuilder);
				break;
			case EXTRA_DEFAULT_FLOWERS:
				DefaultBiomeFeatures.addExtraDefaultFlowers(genBuilder);
				break;
			case DEFAULT_GRASS:
				DefaultBiomeFeatures.addDefaultGrass(genBuilder);
				break;
			case TAIGA_GRASS:
				DefaultBiomeFeatures.addTaigaGrass(genBuilder);
				break;
			case PLAINS_TALL_GRASS:
				DefaultBiomeFeatures.addPlainsTallGrass(genBuilder);
				break;
			case DEFAULT_MUSHROOMS:
				DefaultBiomeFeatures.addDefaultMushrooms(genBuilder);
				break;
			case DEFAULT_VEGETATION:
				DefaultBiomeFeatures.addDefaultVegetation(genBuilder);
				break;
			case BADLANDS_VEGETATION:
				DefaultBiomeFeatures.addBadlandsVegetation(genBuilder);
				break;
			case JUNGLE_VEGETATION:
				DefaultBiomeFeatures.addJungleVegetation(genBuilder);
				break;
			case DESERT_VEGETATION:
				DefaultBiomeFeatures.addDesertVegetation(genBuilder);
				break;
			case SWAMP_VEGETATION:
				DefaultBiomeFeatures.addSwampVegetation(genBuilder);
				break;
			case DESERT_FEATURES:
				DefaultBiomeFeatures.addDesertFeatures(genBuilder);
				break;
			case FOSSILS:
				DefaultBiomeFeatures.addFossils(genBuilder);
				break;
			case KELP:
				DefaultBiomeFeatures.addKelp(genBuilder);
				break;
			case SEAGRASS_ON_STONE:
				DefaultBiomeFeatures.addSeagrassOnStone(genBuilder);
				break;
			case LESS_KELP:
				DefaultBiomeFeatures.addLessKelp(genBuilder);
				break;
			case SPRINGS:
				DefaultBiomeFeatures.addSprings(genBuilder);
				break;
			case ICEBERGS:
				DefaultBiomeFeatures.addIcebergs(genBuilder);
				break;
			case BLUE_ICE:
				DefaultBiomeFeatures.addBlueIce(genBuilder);
				break;
			case FROZEN_TOP_LAYER:
				DefaultBiomeFeatures.addFrozenTopLayer(genBuilder);
				break;
		}
	}
}
