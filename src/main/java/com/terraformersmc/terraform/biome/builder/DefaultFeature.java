package com.terraformersmc.terraform.biome.builder;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

public enum DefaultFeature {
	LAND_CARVERS("land_carvers"),
	OCEAN_CARVERS("ocean_carvers"),
	STRUCTURES("structures"),
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
	GIANT_SPRUCE_TAIGA_TREES("giant_spruce_taiga_trees"),
	MEGA_SPRUCE_TAIGA_TREES("mega_spruce_taiga_trees"),
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
	@Deprecated
	DESSERT_FEATURES("dessert_features"),
	FOSSILS("fossils"),
	KELP("kelp"),
	SEAGRASS_ON_STONE("seagrass_on_stone"),
	SEAGRASS("seagrass"),
	MORE_SEAGRASS("more_seagrass"),
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

	public void add(GenerationSettings.Builder builder) {
		switch (this) {
			case LAND_CARVERS:
				DefaultBiomeFeatures.addLandCarvers(builder);
				break;
			case OCEAN_CARVERS:
				DefaultBiomeFeatures.addOceanCarvers(builder);
				break;
			case STRUCTURES:
				DefaultBiomeFeatures.addDefaultUndergroundStructures(builder);
				break;
			case LAKES:
				DefaultBiomeFeatures.addDefaultLakes(builder);
				break;
			case DESERT_LAKES:
				DefaultBiomeFeatures.addDesertLakes(builder);
				break;
			case DUNGEONS:
				DefaultBiomeFeatures.addDungeons(builder);
				break;
			case MINEABLES:
				DefaultBiomeFeatures.addMineables(builder);
				break;
			case ORES:
				DefaultBiomeFeatures.addDefaultOres(builder);
				break;
			case EXTRA_GOLD:
				DefaultBiomeFeatures.addExtraGoldOre(builder);
				break;
			case EMERALD_ORE:
				DefaultBiomeFeatures.addEmeraldOre(builder);
				break;
			case INFECTED_STONE:
				DefaultBiomeFeatures.addInfestedStone(builder);
				break;
			case DISKS:
				DefaultBiomeFeatures.addDefaultDisks(builder);
				break;
			case CLAY:
				DefaultBiomeFeatures.addClay(builder);
				break;
			case MOSSY_ROCKS:
				DefaultBiomeFeatures.addMossyRocks(builder);
				break;
			case LARGE_FERNS:
				DefaultBiomeFeatures.addLargeFerns(builder);
				break;
			case SWEET_BERRY_BUSHES:
				DefaultBiomeFeatures.addSweetBerryBushes(builder);
				break;
			case SWEET_BERRY_BUSHES_SNOWY:
				DefaultBiomeFeatures.addSweetBerryBushesSnowy(builder);
				break;
			case BAMBOO:
				DefaultBiomeFeatures.addBamboo(builder);
				break;
			case BAMBOO_JUNGLE_TREES:
				DefaultBiomeFeatures.addBambooJungleTrees(builder);
				break;
			case TAIGA_TREES:
				DefaultBiomeFeatures.addTaigaTrees(builder);
				break;
			case WATER_BIOME_OAK_TREES:
				DefaultBiomeFeatures.addWaterBiomeOakTrees(builder);
				break;
			case BIRCH_TREES:
				DefaultBiomeFeatures.addBirchTrees(builder);
				break;
			case FOREST_TREES:
				DefaultBiomeFeatures.addForestTrees(builder);
				break;
			case TALL_BIRCH_TREES:
				DefaultBiomeFeatures.addTallBirchTrees(builder);
				break;
			case SAVANNA_TREES:
				DefaultBiomeFeatures.addSavannaTrees(builder);
				break;
			case EXTRA_SAVANNA_TREES:
				DefaultBiomeFeatures.addExtraSavannaTrees(builder);
				break;
			case MOUNTAIN_TREES:
				DefaultBiomeFeatures.addMountainTrees(builder);
				break;
			case EXTRA_MOUNTAIN_TREES:
				DefaultBiomeFeatures.addExtraMountainTrees(builder);
				break;
			case JUNGLE_TREES:
				DefaultBiomeFeatures.addJungleTrees(builder);
				break;
			case JUNGLE_EDGE_TREES:
				DefaultBiomeFeatures.addJungleEdgeTrees(builder);
				break;
			case BADLANDS_PLATEAU_TREES:
				DefaultBiomeFeatures.addBadlandsPlateauTrees(builder);
				break;
			case SNOWY_SPRUCE_TREES:
				DefaultBiomeFeatures.addSnowySpruceTrees(builder);
				break;
			case GIANT_SPRUCE_TAIGA_TREES:
				builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.TREES_GIANT_SPRUCE);
				break;
			case MEGA_SPRUCE_TAIGA_TREES:
				builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.TREES_GIANT);
				break;
			case JUNGLE_GRASS:
				DefaultBiomeFeatures.addJungleGrass(builder);
				break;
			case SAVANNA_TALL_GRASS:
				DefaultBiomeFeatures.addSavannaTallGrass(builder);
				break;
			case SHATTERED_SAVANNA_TALL_GRASS:
				DefaultBiomeFeatures.addShatteredSavannaGrass(builder);
				break;
			case SAVANNA_GRASS:
				DefaultBiomeFeatures.addSavannaGrass(builder);
				break;
			case BADLANDS_GRASS:
				DefaultBiomeFeatures.addBadlandsGrass(builder);
				break;
			case FOREST_FLOWERS:
				DefaultBiomeFeatures.addForestFlowers(builder);
				break;
			case FOREST_GRASS:
				DefaultBiomeFeatures.addForestGrass(builder);
				break;
			case SWAMP_FEATURES:
				DefaultBiomeFeatures.addSwampFeatures(builder);
				break;
			case MUSHROOM_FIELDS_FEATURES:
				DefaultBiomeFeatures.addMushroomFieldsFeatures(builder);
				break;
			case PLAINS_FEATURES:
				DefaultBiomeFeatures.addPlainsFeatures(builder);
				break;
			case DESERT_DEAD_BUSHES:
				DefaultBiomeFeatures.addDesertDeadBushes(builder);
				break;
			case GIANT_TAIGA_GRASS:
				DefaultBiomeFeatures.addGiantTaigaGrass(builder);
				break;
			case DEFAULT_FLOWERS:
				DefaultBiomeFeatures.addDefaultFlowers(builder);
				break;
			case EXTRA_DEFAULT_FLOWERS:
				DefaultBiomeFeatures.addExtraDefaultFlowers(builder);
				break;
			case DEFAULT_GRASS:
				DefaultBiomeFeatures.addDefaultGrass(builder);
				break;
			case TAIGA_GRASS:
				DefaultBiomeFeatures.addTaigaGrass(builder);
				break;
			case PLAINS_TALL_GRASS:
				DefaultBiomeFeatures.addPlainsTallGrass(builder);
				break;
			case DEFAULT_MUSHROOMS:
				DefaultBiomeFeatures.addDefaultMushrooms(builder);
				break;
			case DEFAULT_VEGETATION:
				DefaultBiomeFeatures.addDefaultVegetation(builder);
				break;
			case BADLANDS_VEGETATION:
				DefaultBiomeFeatures.addBadlandsVegetation(builder);
				break;
			case JUNGLE_VEGETATION:
				DefaultBiomeFeatures.addJungleVegetation(builder);
				break;
			case DESERT_VEGETATION:
				DefaultBiomeFeatures.addDesertVegetation(builder);
				break;
			case SWAMP_VEGETATION:
				DefaultBiomeFeatures.addSwampVegetation(builder);
				break;
			case DESERT_FEATURES:
			case DESSERT_FEATURES:
				DefaultBiomeFeatures.addDesertFeatures(builder);
				break;
			case FOSSILS:
				DefaultBiomeFeatures.addFossils(builder);
				break;
			case KELP:
				DefaultBiomeFeatures.addKelp(builder);
				break;
			case SEAGRASS_ON_STONE:
				DefaultBiomeFeatures.addSeagrassOnStone(builder);
				break;
			case SEAGRASS:
				builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEAGRASS_WARM);
				break;
			case MORE_SEAGRASS:
				builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEAGRASS_DEEP_WARM);
				break;
			case LESS_KELP:
				DefaultBiomeFeatures.addLessKelp(builder);
				break;
			case SPRINGS:
				DefaultBiomeFeatures.addSprings(builder);
				break;
			case ICEBERGS:
				DefaultBiomeFeatures.addIcebergs(builder);
				break;
			case BLUE_ICE:
				DefaultBiomeFeatures.addBlueIce(builder);
				break;
			case FROZEN_TOP_LAYER:
				DefaultBiomeFeatures.addFrozenTopLayer(builder);
				break;
		}
	}
}
