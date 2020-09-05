package com.terraformersmc.terraform.biome.builder;

import com.terraformersmc.terraform.util.TerraformBiomeSets;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.placer.DoublePlantPlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TerraformBiome {
	private final Biome.Builder biome;
	private final BiomeEffects.Builder effects;
	private final GenerationSettings.Builder generationSettings;
	private final SpawnSettings.Builder spawnSettings;

	private TerraformBiome(Biome.Builder biome,
						   BiomeEffects.Builder effects,
						   GenerationSettings.Builder generationSettings,
						   SpawnSettings.Builder spawnSettings,
						   List<SpawnSettings.SpawnEntry> spawns) {
		this.biome = biome;
		this.effects = effects;
		this.generationSettings = generationSettings;
		this.spawnSettings = spawnSettings;
		for (SpawnSettings.SpawnEntry entry : spawns) {
			spawnSettings.spawn(entry.type.getSpawnGroup(), entry);
		}
	}

	public void setGrassAndFoliageColors(int grassColor, int foliageColor) {
		this.effects.grassColor(grassColor)
				.foliageColor(foliageColor);
	}

	public void setSpawnChance(float spawnChance) {
		this.spawnSettings.creatureSpawnProbability(spawnChance);
	}

	public static TerraformBiome.Builder builder(RegistryKey<Biome> key) {
		return new com.terraformersmc.terraform.biome.builder.TerraformBiome.Builder(key);
	}

	public static final class Builder extends BuilderBiomeSettings {
		private final Biome.Builder biome = new Biome.Builder();
		private final BiomeEffects.Builder effects = new BiomeEffects.Builder();
		private final GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
		private final SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
		private List<DefaultFeature> defaultFeatures = new ArrayList<>();
		private List<FeatureEntry> features = new ArrayList<>();
		private List<ConfiguredStructureFeature<? extends FeatureConfig, ? extends StructureFeature<? extends FeatureConfig>>> structureFeatures = new ArrayList<>();
		private Map<ConfiguredFeature, Integer> treeFeatures = new HashMap<>();
		private Map<ConfiguredFeature, Integer> rareTreeFeatures = new HashMap<>();
		private Map<BlockState, Integer> plantFeatures = new HashMap<>();
		private Map<BlockState, Integer> doublePlantFeatures = new HashMap<>();
		private List<SpawnSettings.SpawnEntry> spawnEntries = new ArrayList<>();
		private int grassColor = -1;
		private int foliageColor = -1;
		private float spawnChance = -1;
		private boolean template = false;
		private boolean slimeSpawnBiome = false;
		// NOTE: Make sure to add any additional fields to the Template copy code down below!

		Builder(RegistryKey<Biome> key) {
			super();
		}

		Builder(com.terraformersmc.terraform.biome.builder.TerraformBiome.Builder existing) { // Template copy code
			super(existing);

			this.defaultFeatures.addAll(existing.defaultFeatures);
			this.features.addAll(existing.features);
			this.structureFeatures.addAll(existing.structureFeatures);
			this.treeFeatures.putAll(existing.treeFeatures);
			this.rareTreeFeatures.putAll(existing.rareTreeFeatures);
			this.plantFeatures.putAll(existing.plantFeatures);
			this.doublePlantFeatures.putAll(existing.doublePlantFeatures);
			this.spawnEntries.addAll(existing.spawnEntries);

			this.grassColor = existing.grassColor;
			this.foliageColor = existing.foliageColor;
			this.spawnChance = existing.spawnChance;
			this.slimeSpawnBiome = existing.slimeSpawnBiome;
		}

		@SuppressWarnings("unchecked")
		public Biome build() {
			if (template) {
				throw new IllegalStateException("Tried to call build() on a frozen Builder instance!");
			}

			// Add SpawnEntries
			TerraformBiome biome = new TerraformBiome(this.biome, this.effects, this.generationSettings, this.spawnSettings, this.spawnEntries);

			// Set grass and foliage colors
			biome.setGrassAndFoliageColors(this.grassColor, this.foliageColor);

			// Set spawn chance
			biome.setSpawnChance(this.spawnChance);

			// Add as a slime spawn biome if needed
			if (this.slimeSpawnBiome) {
				TerraformBiomeSets.addSlimeSpawnBiome(key);
			}

			// Add structures
			for (ConfiguredStructureFeature<? extends FeatureConfig, ? extends StructureFeature<? extends FeatureConfig>> structure : structureFeatures) {
				generationSettings.structureFeature(structure);
			}

			// Tree Feature stuff
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

					// TODO: This will not work, as each configured feature has to have a registered ID
					// TODO: Potentially auto-configure based on <biome-id>/<tree-feature-id>
					generationSettings.feature(
							GenerationStep.Feature.VEGETAL_DECORATION,
							feature.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(count, 0.1F * weight, 1)))
					);
				}
			}

			// Rare tree features

			for (Map.Entry<ConfiguredFeature, Integer> tree : rareTreeFeatures.entrySet()) {
				ConfiguredFeature feature = tree.getKey();
				int chance = tree.getValue();

				generationSettings.feature(
						GenerationStep.Feature.VEGETAL_DECORATION,
						// TODO: This will not work, as each configured feature has to have a registered ID
						// TODO: Potentially auto-configure based on <biome-id>/<tree-feature-id>
						(ConfiguredFeature) feature.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).applyChance(chance)
				);
			}

			// Add any minecraft (default) features

			for (DefaultFeature defaultFeature : defaultFeatures) {
				defaultFeature.add(generationSettings);
			}

			// Add custom features that don't fit in the templates

			for (FeatureEntry feature : features) {
				generationSettings.feature(feature.getStep(), feature.getFeature());
			}

			// Add Plant decoration features

			WeightedBlockStateProvider weightedStateProvider = new WeightedBlockStateProvider();
			int chanceTotal = 0;
			for (Map.Entry<BlockState, Integer> plant : plantFeatures.entrySet()) {
				weightedStateProvider.addState(plant.getKey(), plant.getValue());
				chanceTotal += plant.getValue();
			}
			generationSettings.feature(
					GenerationStep.Feature.VEGETAL_DECORATION,
					// TODO: This will not work, as each configured feature has to have a registered ID
					// TODO: Potentially auto-configure based on <biome-id>/<tree-feature-id>
					Feature.RANDOM_PATCH.configure(
							new RandomPatchFeatureConfig.Builder(weightedStateProvider, new SimpleBlockPlacer())
									.tries(32)
									.build())
							.decorate(
									ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE.repeat(chanceTotal)));

			// Add Double Plant decoration features

			for (Map.Entry<BlockState, Integer> doublePlant : doublePlantFeatures.entrySet()) {
				generationSettings.feature(
						GenerationStep.Feature.VEGETAL_DECORATION,
						// TODO: This will not work, as each configured feature has to have a registered ID
						// TODO: Potentially auto-configure based on <biome-id>/<tree-feature-id>
						Feature.RANDOM_PATCH.configure(
								new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(doublePlant.getKey()), new DoublePlantPlacer())
										.tries(64)
										.cannotProject()
										.build())
								.decorate(ConfiguredFeatures.Decorators.SPREAD_32_ABOVE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeat(doublePlant.getValue())
				);
			}

			return biome.biome
					.generationSettings(biome.generationSettings.build())
					.effects(biome.effects.build())
					.spawnSettings(biome.spawnSettings.build())
					.build();
		}

		@Override
		public <SC extends SurfaceConfig> com.terraformersmc.terraform.biome.builder.TerraformBiome.Builder configureSurfaceBuilder(SurfaceBuilder<SC> builder, SC config) {
			super.configureSurfaceBuilder(builder, config);

			return this;
		}

		@Override
		public com.terraformersmc.terraform.biome.builder.TerraformBiome.Builder surfaceBuilder(ConfiguredSurfaceBuilder<?> surfaceBuilder) {
			super.surfaceBuilder(surfaceBuilder);

			return this;
		}

		@Override
		public com.terraformersmc.terraform.biome.builder.TerraformBiome.Builder precipitation(Biome.Precipitation precipitation) {
			super.precipitation(precipitation);

			return this;
		}

		@Override
		public com.terraformersmc.terraform.biome.builder.TerraformBiome.Builder category(Biome.Category category) {
			super.category(category);

			return this;
		}

		@Override
		public com.terraformersmc.terraform.biome.builder.TerraformBiome.Builder depth(float depth) {
			super.depth(depth);

			return this;
		}

		@Override
		public com.terraformersmc.terraform.biome.builder.TerraformBiome.Builder scale(float scale) {
			super.scale(scale);

			return this;
		}

		@Override
		public com.terraformersmc.terraform.biome.builder.TerraformBiome.Builder temperature(float temperature) {
			super.temperature(temperature);

			return this;
		}

		@Override
		public com.terraformersmc.terraform.biome.builder.TerraformBiome.Builder downfall(float downfall) {
			super.downfall(downfall);

			return this;
		}

		@Override
		public com.terraformersmc.terraform.biome.builder.TerraformBiome.Builder waterColor(int color) {
			super.waterColor(color);

			return this;
		}

		@Override
		public com.terraformersmc.terraform.biome.builder.TerraformBiome.Builder waterFogColor(int color) {
			super.waterFogColor(color);

			return this;
		}

		@Override
		public com.terraformersmc.terraform.biome.builder.TerraformBiome.Builder effects(BiomeEffects effects) {
			super.effects(effects);
			return this;
		}

		public com.terraformersmc.terraform.biome.builder.TerraformBiome.Builder setSpawnChance(float spawnChance) {
			this.spawnChance = spawnChance;
			return this;
		}

		public TerraformBiome.Builder addTreeFeature(ConfiguredFeature feature, int numPerChunk) {
			this.treeFeatures.put(feature, numPerChunk);
			return this;
		}

		public TerraformBiome.Builder addRareTreeFeature(ConfiguredFeature feature, int chance) {
			this.rareTreeFeatures.put(feature, chance);
			return this;
		}

		public TerraformBiome.Builder addGrassFeature(BlockState blockState, int count) {
			this.plantFeatures.put(blockState, count);
			return this;
		}

		public TerraformBiome.Builder addDoubleGrassFeature(BlockState blockState, int count) {
			this.doublePlantFeatures.put(blockState, count);
			return this;
		}

		public TerraformBiome.Builder addCustomFeature(GenerationStep.Feature step, ConfiguredFeature feature) {
			this.features.add(new FeatureEntry(step, feature));
			return this;
		}

		public TerraformBiome.Builder addSpawnEntry(SpawnSettings.SpawnEntry entry) {
			this.spawnEntries.add(entry);
			return this;
		}

		public <FC extends FeatureConfig> TerraformBiome.Builder addStructureFeature(ConfiguredStructureFeature<? extends FeatureConfig, ? extends StructureFeature<? extends FeatureConfig>> stucture) {
			this.structureFeatures.add(stucture);
			return this;
		}

		public TerraformBiome.Builder addStructureFeatures(ConfiguredStructureFeature<? extends FeatureConfig, ? extends StructureFeature<? extends FeatureConfig>>... stuctures) {
			this.structureFeatures.addAll(Arrays.asList(stuctures));
			return this;
		}

		public TerraformBiome.Builder addDefaultFeature(DefaultFeature feature) {
			defaultFeatures.add(feature);
			return this;
		}

		public TerraformBiome.Builder addDefaultFeatures(DefaultFeature... features) {
			defaultFeatures.addAll(Arrays.asList(features));
			return this;
		}

		public TerraformBiome.Builder grassColor(int color) {
			grassColor = color;
			return this;
		}

		public TerraformBiome.Builder foliageColor(int color) {
			foliageColor = color;
			return this;
		}

		public TerraformBiome.Builder addDefaultSpawnEntries() {
			this.addDefaultCreatureSpawnEntries()
					.addDefaultAmbientSpawnEntries()
					.addDefaultMonsterSpawnEntries();
			return this;
		}

		public TerraformBiome.Builder addDefaultCreatureSpawnEntries() {
			this.addSpawnEntry(new SpawnSettings.SpawnEntry(EntityType.SHEEP, 12, 4, 4))
					.addSpawnEntry(new SpawnSettings.SpawnEntry(EntityType.PIG, 10, 4, 4))
					.addSpawnEntry(new SpawnSettings.SpawnEntry(EntityType.CHICKEN, 10, 4, 4))
					.addSpawnEntry(new SpawnSettings.SpawnEntry(EntityType.COW, 8, 4, 4));
			return this;
		}

		public TerraformBiome.Builder addDefaultAmbientSpawnEntries() {
			this.addSpawnEntry(new SpawnSettings.SpawnEntry(EntityType.BAT, 10, 8, 8));
			return this;
		}

		public TerraformBiome.Builder addDefaultMonsterSpawnEntries() {
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

		public TerraformBiome.Builder slimeSpawnBiome() {
			slimeSpawnBiome = true;
			return this;
		}
	}

	public static final class Template {
		private final com.terraformersmc.terraform.biome.builder.TerraformBiome.Builder builder;

		public Template(com.terraformersmc.terraform.biome.builder.TerraformBiome.Builder builder) {
			this.builder = builder;
			builder.template = true;
		}

		public com.terraformersmc.terraform.biome.builder.TerraformBiome.Builder builder() {
			return new com.terraformersmc.terraform.biome.builder.TerraformBiome.Builder(this.builder);
		}
	}
}
