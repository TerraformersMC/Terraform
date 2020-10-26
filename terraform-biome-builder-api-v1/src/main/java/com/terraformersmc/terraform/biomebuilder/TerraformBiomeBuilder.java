package com.terraformersmc.terraform.biomebuilder;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;

public final class TerraformBiomeBuilder extends BuilderBiomeSettings {
	private final ArrayList<DefaultFeature> defaultFeatures = new ArrayList<>();
	private final ArrayList<FeatureEntry> features = new ArrayList<>();
	private final ArrayList<ConfiguredStructureFeature<? extends FeatureConfig, ? extends StructureFeature<? extends FeatureConfig>>> structureFeatures = new ArrayList<>();
	private final ArrayList<SpawnSettings.SpawnEntry> spawnEntries = new ArrayList<>();
	private float spawnChance = -1;
	private boolean template = false;
	private boolean slimeSpawnBiome = false;
	private boolean playerSpawnFriendly = false;
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
		this.spawnEntries.addAll(existing.spawnEntries);

		this.spawnChance = existing.spawnChance;
		this.slimeSpawnBiome = existing.slimeSpawnBiome;
		this.playerSpawnFriendly = existing.playerSpawnFriendly;
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

		if (playerSpawnFriendly) {
			spawnSettings.playerSpawnFriendly();
		}

		// Add structures
		for (ConfiguredStructureFeature<? extends FeatureConfig, ? extends StructureFeature<? extends FeatureConfig>> structure : structureFeatures) {
			generationSettings.structureFeature(structure);
		}

		// Add any minecraft (default) features

		for (DefaultFeature defaultFeature : defaultFeatures) {
			defaultFeature.add(generationSettings);
		}

		// Add custom features that don't fit in the templates

		for (FeatureEntry feature : features) {
			generationSettings.feature(feature.getStep(), feature.getFeature());
		}

		builder.generationSettings(generationSettings.build());
		builder.spawnSettings(spawnSettings.build());

		return builder.build();
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

	@SafeVarargs
	public final TerraformBiomeBuilder addStructureFeatures(ConfiguredStructureFeature<? extends FeatureConfig, ? extends StructureFeature<? extends FeatureConfig>>... stuctures) {
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

	public TerraformBiomeBuilder playerSpawnFriendly() {
		playerSpawnFriendly = true;
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
