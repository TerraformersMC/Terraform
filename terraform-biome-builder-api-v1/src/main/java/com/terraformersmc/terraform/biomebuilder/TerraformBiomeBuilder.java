package com.terraformersmc.terraform.biomebuilder;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placer.DoublePlantPlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;

import java.util.*;

import static net.minecraft.world.gen.feature.ConfiguredFeatures.Decorators.SPREAD_32_ABOVE;

public final class TerraformBiomeBuilder extends BuilderBiomeSettings {
	public static final Map<Biome, TerraformBiomeBuilder> EXTENDED_BIOMES = new HashMap<>();

	private final List<DefaultFeature> defaultFeatures = new ArrayList<>();
	private final List<FeatureEntry> features = new ArrayList<>();
	private final List<ConfiguredStructureFeature<? extends FeatureConfig, ? extends StructureFeature<? extends FeatureConfig>>> structureFeatures = new ArrayList<>();
	private final Map<ConfiguredFeature, Integer> treeFeatures = new HashMap<>();
	private final Map<ConfiguredFeature, Integer> rareTreeFeatures = new HashMap<>();
	private final Map<BlockState, Integer> plantFeatures = new HashMap<>();
	private final Map<BlockState, Integer> doublePlantFeatures = new HashMap<>();
	private final List<SpawnSettings.SpawnEntry> spawnEntries = new ArrayList<>();
	private float spawnChance = -1;
	private boolean template = false;
	private boolean slimeSpawnBiome = false;
	// NOTE: Make sure to add any additional fields to the Template copy code down below!

	TerraformBiomeBuilder() {
		super();

		parent(null);
	}

	TerraformBiomeBuilder(TerraformBiomeBuilder existing) { // Template copy code
		super(existing);

		this.defaultFeatures.addAll(existing.defaultFeatures);
		this.features.addAll(existing.features);
		this.structureFeatures.addAll(existing.structureFeatures);
		this.treeFeatures.putAll(existing.treeFeatures);
		this.rareTreeFeatures.putAll(existing.rareTreeFeatures);
		this.plantFeatures.putAll(existing.plantFeatures);
		this.doublePlantFeatures.putAll(existing.doublePlantFeatures);
		this.spawnEntries.addAll(existing.spawnEntries);

		this.spawnChance = existing.spawnChance;
		this.slimeSpawnBiome = existing.slimeSpawnBiome;
	}

	@SuppressWarnings("unchecked")
	public Biome build() {
		if (template) {
			throw new IllegalStateException("Tried to call build() on a frozen Builder instance!");
		}

		Biome.Builder builder = new Biome.Builder();
		builder.category(this.category);
		builder.depth(this.depth);
		builder.scale(this.scale);
		builder.downfall(this.downfall);
		builder.precipitation(this.precipitation);
		builder.temperature(this.temperature);
		builder.effects(this.effects.build());

		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder().surfaceBuilder(this.surfaceBuilder);

		// Set grass and foliage colors
		// todo: grass and foliage
//		biome.setGrassAndFoliageColors(this.grassColor, this.foliageColor);

		SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
		// Set spawn chance
		// todo: set spawn chance
		spawnSettings.creatureSpawnProbability(this.spawnChance);
		for (SpawnSettings.SpawnEntry spawnEntry : spawnEntries) {
			spawnSettings.spawn(spawnEntry.type.getSpawnGroup(), spawnEntry);
		}

		// Add as a slime spawn biome if needed
		if (this.slimeSpawnBiome) {
			//todo: slime biomes
//			TerraformBiomeSets.addSlimeSpawnBiome(biome);
		}

		// Add structures
		for (ConfiguredStructureFeature<? extends FeatureConfig, ? extends StructureFeature<? extends FeatureConfig>> structure : structureFeatures) {
			generationSettings.structureFeature(structure);
		}

		/*// Tree Feature stuff
		if (treeFeatures.size() > 0) {

			// Determine the total tree count

			int totalTreesPerChunk = 0;
			for (Integer count : treeFeatures.values()) {
				totalTreesPerChunk += count;
			}

			// Add each tree

			for (Map.Entry<ConfiguredFeature, Integer> tree : treeFeatures.entrySet()) {
				ConfiguredFeature feature = tree.getKey();
				int count = tree.getValue();

				float weight = (float) count / totalTreesPerChunk;

				generationSettings.feature(
						GenerationStep.Feature.VEGETAL_DECORATION,
						feature.decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(count, 0.1F * weight, 1)))
				);
			}
		}

		// Rare tree features

		for (Map.Entry<ConfiguredFeature, Integer> tree : rareTreeFeatures.entrySet()) {
			ConfiguredFeature feature = tree.getKey();
			int chance = tree.getValue();

			generationSettings.feature(
					GenerationStep.Feature.VEGETAL_DECORATION,
					feature.decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(chance)))
			);
		}*/

		// Add any minecraft (default) features

		for (DefaultFeature defaultFeature : defaultFeatures) {
			defaultFeature.add(generationSettings);
		}

		// Add custom features that don't fit in the templates

		for (FeatureEntry feature : features) {
			generationSettings.feature(feature.getStep(), feature.getFeature());
		}

		/*// Add Plant decoration features

		WeightedBlockStateProvider weightedStateProvider = new WeightedBlockStateProvider();
		int chanceTotal = 0;
		for (Map.Entry<BlockState, Integer> plant : plantFeatures.entrySet()) {
			weightedStateProvider.addState(plant.getKey(), plant.getValue());
			chanceTotal += plant.getValue();
		}
		generationSettings.feature(
				GenerationStep.Feature.VEGETAL_DECORATION,
				Feature.RANDOM_PATCH.configure(
						new RandomPatchFeatureConfig.Builder(weightedStateProvider, new SimpleBlockPlacer())
								.tries(32)
								.build())
						.decorate(
								SPREAD_32_ABOVE));

		// Add Double Plant decoration features

		for (Map.Entry<BlockState, Integer> doublePlant : doublePlantFeatures.entrySet()) {
			generationSettings.feature(
					GenerationStep.Feature.VEGETAL_DECORATION,
					Feature.RANDOM_PATCH.configure(
							new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(doublePlant.getKey()), new DoublePlantPlacer())
									.tries(64)
									.cannotProject()
									.build())
							.decorate(
									ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP));
		}*/

		builder.generationSettings(generationSettings.build());

		builder.spawnSettings(spawnSettings.build());
		Biome biome = builder.build();
		EXTENDED_BIOMES.put(biome, this);
		return biome;
	}

	@Override
	public <SC extends SurfaceConfig> TerraformBiomeBuilder configureSurfaceBuilder(SurfaceBuilder<SC> builder, SC config) {
		super.configureSurfaceBuilder(builder, config);

		return this;
	}

	@Override
	public TerraformBiomeBuilder surfaceBuilder(ConfiguredSurfaceBuilder<?> surfaceBuilder) {
		super.surfaceBuilder(surfaceBuilder);

		return this;
	}

	@Override
	public TerraformBiomeBuilder precipitation(Biome.Precipitation precipitation) {
		super.precipitation(precipitation);

		return this;
	}

	@Override
	public TerraformBiomeBuilder category(Biome.Category category) {
		super.category(category);

		return this;
	}

	@Override
	public TerraformBiomeBuilder depth(float depth) {
		super.depth(depth);

		return this;
	}

	@Override
	public TerraformBiomeBuilder scale(float scale) {
		super.scale(scale);

		return this;
	}

	@Override
	public TerraformBiomeBuilder temperature(float temperature) {
		super.temperature(temperature);
		return this;
	}

	@Override
	public TerraformBiomeBuilder downfall(float downfall) {
		super.downfall(downfall);
		return this;
	}

	@Override
	public TerraformBiomeBuilder parent(String parent) {
		super.parent(parent);
		return this;
	}

	@Override
	public TerraformBiomeBuilder effects(BiomeEffects.Builder effects) {
		super.effects(effects);
		return this;
	}

	public TerraformBiomeBuilder setSpawnChance(float spawnChance) {
		this.spawnChance = spawnChance;
		return this;
	}

	public TerraformBiomeBuilder addTreeFeature(ConfiguredFeature feature, int numPerChunk) {
		this.treeFeatures.put(feature, numPerChunk);
		return this;
	}

	public TerraformBiomeBuilder addRareTreeFeature(ConfiguredFeature feature, int chance) {
		this.rareTreeFeatures.put(feature, chance);
		return this;
	}

	public TerraformBiomeBuilder addGrassFeature(BlockState blockState, int count) {
		this.plantFeatures.put(blockState, count);
		return this;
	}

	public TerraformBiomeBuilder addDoubleGrassFeature(BlockState blockState, int count) {
		this.doublePlantFeatures.put(blockState, count);
		return this;
	}

	public TerraformBiomeBuilder addFeature(GenerationStep.Feature step, ConfiguredFeature feature) {
		this.features.add(new FeatureEntry(step, feature));
		return this;
	}

	public TerraformBiomeBuilder addSpawnEntry(SpawnSettings.SpawnEntry entry) {
		this.spawnEntries.add(entry);
		return this;
	}

	public <FC extends FeatureConfig> TerraformBiomeBuilder addStructureFeature(ConfiguredStructureFeature<? extends FeatureConfig, ? extends StructureFeature<? extends FeatureConfig>> stucture) {
		this.structureFeatures.add(stucture);
		return this;
	}

	public TerraformBiomeBuilder addStructureFeatures(ConfiguredStructureFeature<? extends FeatureConfig, ? extends StructureFeature<? extends FeatureConfig>>... stuctures) {
		this.structureFeatures.addAll(Arrays.asList(stuctures));
		return this;
	}

	public TerraformBiomeBuilder addDefaultFeature(DefaultFeature feature) {
		defaultFeatures.add(feature);
		return this;
	}

	public TerraformBiomeBuilder addDefaultFeatures(DefaultFeature... features) {
		defaultFeatures.addAll(Arrays.asList(features));
		return this;
	}

	public TerraformBiomeBuilder addDefaultSpawnEntries() {
		this.addDefaultCreatureSpawnEntries()
				.addDefaultAmbientSpawnEntries()
				.addDefaultMonsterSpawnEntries();
		return this;
	}

	public TerraformBiomeBuilder addDefaultCreatureSpawnEntries() {
		this.addSpawnEntry(new SpawnSettings.SpawnEntry(EntityType.SHEEP, 12, 4, 4))
				.addSpawnEntry(new SpawnSettings.SpawnEntry(EntityType.PIG, 10, 4, 4))
				.addSpawnEntry(new SpawnSettings.SpawnEntry(EntityType.CHICKEN, 10, 4, 4))
				.addSpawnEntry(new SpawnSettings.SpawnEntry(EntityType.COW, 8, 4, 4));
		return this;
	}

	public TerraformBiomeBuilder addDefaultAmbientSpawnEntries() {
		this.addSpawnEntry(new SpawnSettings.SpawnEntry(EntityType.BAT, 10, 8, 8));
		return this;
	}

	public TerraformBiomeBuilder addDefaultMonsterSpawnEntries() {
		this.addSpawnEntry(new SpawnSettings.SpawnEntry(EntityType.SPIDER, 100, 4, 4))
				.addSpawnEntry(new SpawnSettings.SpawnEntry(EntityType.ZOMBIE, 95, 4, 4))
				.addSpawnEntry(new SpawnSettings.SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1))
				.addSpawnEntry(new SpawnSettings.SpawnEntry(EntityType.SKELETON, 100, 4, 4))
				.addSpawnEntry(new SpawnSettings.SpawnEntry(EntityType.CREEPER, 100, 4, 4))
				.addSpawnEntry(new SpawnSettings.SpawnEntry(EntityType.SLIME, 100, 4, 4))
				.addSpawnEntry(new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 10, 1, 4))
				.addSpawnEntry(new SpawnSettings.SpawnEntry(EntityType.WITCH, 5, 1, 1));
		return this;
	}

	public TerraformBiomeBuilder slimeSpawnBiome() {
		slimeSpawnBiome = true;
		return this;
	}

	void markTemplate() {
		this.template = true;
	}

	public static TerraformBiomeBuilder create() {
		return new TerraformBiomeBuilder();
	}

	public static TerraformBiomeBuilder create(TerraformBiomeBuilder existing) {
		return new TerraformBiomeBuilder(existing);
	}
}
