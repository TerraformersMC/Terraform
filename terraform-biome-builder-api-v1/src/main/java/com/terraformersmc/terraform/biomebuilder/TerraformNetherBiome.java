//package com.terraformersmc.terraform.biome.builder;
//
//import com.terraformersmc.terraform.mixinterface.FogDensityControlBiome;
//import net.minecraft.client.sound.MusicType;
//import net.minecraft.particle.ParticleEffect;
//import net.minecraft.sound.*;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.biome.BiomeEffects;
//import net.minecraft.world.biome.BiomeParticleConfig;
//import net.minecraft.world.gen.GenerationStep;
//import net.minecraft.world.gen.feature.ConfiguredFeature;
//import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
//import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
//import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
//import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * A base class to construct data driven nether biomes
// */
//public class TerraformNetherBiome extends Biome implements FogDensityControlBiome {
//
//	private float fogDensity;
//
//	/**
//	 * Constructs a new nether biome from a nether biome builder
//	 * @param builder The nether biome builder to construct a biome from
//	 */
//	public TerraformNetherBiome(Builder builder) {
//		super(new Biome.Settings()
//			.surfaceBuilder(builder.surfaceBuilder)
//			.precipitation(Precipitation.NONE)
//			.category(Category.NETHER)
//			.depth(0.1F)
//			.scale(0.2F)
//			.temperature(2.0F)
//			.downfall(0.0F)
//			.effects(new BiomeEffects.Builder()
//				.waterColor(4159204)
//				.waterFogColor(4341314)
//				.fogColor(builder.fogColor)
//				.loopSound(builder.loopSound)
//				.moodSound(builder.moodSound)
//				.additionsSound(builder.additionSound)
//				.music(builder.music)
//				.particleConfig(builder.particle)
//				.build())
//			.noises(builder.noisePoints));
//		builder.features.forEach((feature) -> this.addFeature(feature.getStep(), feature.getFeature()));
//		builder.structureFeatures.forEach(this::addStructureFeature);
//		builder.spawns.forEach((spawn) -> this.addSpawn(spawn.type.getSpawnGroup(), spawn));
//
//		fogDensity = builder.fogDensity;
//	}
//
//	@Override
//	public float fogMultiplier() {
//		return this.fogDensity;
//	}
//
//	/**
//	 * A helper class to easily build nether biomes
//	 */
//	public static final class Builder {
//
//		/**
//		 * Whether or not the builder is a template instance or not
//		 *
//		 * Defaults to false.
//		 */
//		private boolean template = false;
//
//		/**
//		 * The configured surface builder for the biome.
//		 *
//		 * Defaults to the standard nether surface builder.
//		 */
//		private ConfiguredSurfaceBuilder<? extends SurfaceConfig> surfaceBuilder = new ConfiguredSurfaceBuilder<>(SurfaceBuilder.NETHER, SurfaceBuilder.NETHER_CONFIG);
//
//		/**
//		 * The fog color for the biome.
//		 *
//		 * Defaults to the color of the fog in the nether wastes.
//		 */
//		private int fogColor = 3344392;
//
//		/**
//		 * The fog density multiplier for the biome.
//		 *
//		 * Defaults to 1.0.
//		 */
//		private float fogDensity = 1.0f;
//
//		/**
//		 * The looping sound for the biome.
//		 *
//		 * Defaults to the nether wastes loop.
//		 */
//		private SoundEvent loopSound = SoundEvents.AMBIENT_NETHER_WASTES_LOOP;
//
//		/**
//		 * The mood sound for the biome.
//		 *
//		 * Defaults to the nether wastes mood sound.
//		 */
//		private BiomeMoodSound moodSound = new BiomeMoodSound(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2.0D);
//
//		/**
//		 * The addition sound for the biome.
//		 *
//		 * Defaults to the nether wastes addition sound.
//		 */
//		private BiomeAdditionsSound additionSound = new BiomeAdditionsSound(SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS, 0.0111D);
//
//		/**
//		 * The music type for the biome.
//		 *
//		 * Defaults to the nether wastes music type.
//		 */
//		private MusicSound music = MusicType.method_27283(SoundEvents.MUSIC_NETHER_NETHER_WASTES);
//
//		/**
//		 * The ambient particle configuration for the biome.
//		 */
//		private BiomeParticleConfig particle;
//
//		/**
//		 * A list of the custom features for the biome.
//		 */
//		private List<FeatureEntry> features = new ArrayList<>();
//
//		/**
//		 * A lise of the custom structure features for the biome.
//		 */
//		private List<ConfiguredStructureFeature> structureFeatures = new ArrayList<>();
//
//		/**
//		 * A list of all of the spawn entries for the biome.
//		 */
//		private List<SpawnSettings.SpawnEntry> spawns = new ArrayList<>();
//
//		/**
//		 * A list of all of the noise points for the biome.
//		 */
//		private List<Biome.MixedNoisePoint> noisePoints = new ArrayList<>();
//
//		/**
//		 * Constructs a copy of the builder from the parent.
//		 * @param parent The parent builder to copy from.
//		 */
//		public Builder(Builder parent) {
//			this.surfaceBuilder = parent.surfaceBuilder;
//			this.fogColor = parent.fogColor;
//			this.fogDensity = parent.fogDensity;
//			this.loopSound = parent.loopSound;
//			this.moodSound = parent.moodSound;
//			this.additionSound = parent.additionSound;
//			this.music = parent.music;
//			this.particle = parent.particle;
//			this.features = parent.features;
//			this.structureFeatures = parent.structureFeatures;
//			this.spawns = parent.spawns;
//			this.noisePoints = parent.noisePoints;
//		}
//
//		/**
//		 * Constructs a new default builder object.
//		 */
//		public Builder() { }
//
//		/**
//		 * Creates a copy of the current builder.
//		 * @return Returns a copy of the current builder.
//		 */
//		public Builder copy() {
//			return new Builder(this);
//		}
//
//		/**
//		 * Sets the fog color of the biome to the given fog color.
//		 * @param color The color to set the fog of the biome to.
//		 * @return The biome builder with the modified fog color.
//		 */
//		public Builder fogColor(int color) {
//			this.fogColor = color;
//			return this;
//		}
//
//		/**
//		 * Sets the fog density of the biome to the given density.
//		 * @param density The density to set the biome fog to.
//		 * @return The biome builder with the modified fog density.
//		 */
//		public Builder fogDensity(float density) {
//			this.fogDensity = density;
//			return this;
//		}
//
//		/**
//		 * Sets the loop sound of the biome to the given sound event.
//		 * @param sound The sound event to set as the loop sound for the biome.
//		 * @return The biome builder with the modified loop sound.
//		 */
//		public Builder loopSound(SoundEvent sound) {
//			this.loopSound = sound;
//			return this;
//		}
//
//		/**
//		 * Sets the mood sound of the biome to the given mood sound configuration.
//		 * @param sound The sound event to set as the mood sound for the biome.
//		 * @param tickInterval The time in ticks between plays of the mood sound.
//		 * @param spawnRange The spawn range of the mood sound.
//		 * @param extraDistance The extra distance of the mood sound.
//		 * @return The biome builder with the modified mood sound.
//		 */
//		public Builder moodSound(SoundEvent sound, int tickInterval, int spawnRange, double extraDistance) {
//			this.moodSound = new BiomeMoodSound(sound, tickInterval, spawnRange, extraDistance);
//			return this;
//		}
//
//		/**
//		 * Sets the mood sound of the biome to the given sound event.
//		 * @param sound The sound event to set as the mood sound for the biome.
//		 * @return The biome builder with the modified mood sound.
//		 */
//		public Builder moodSound(SoundEvent sound) {
//			return this.moodSound(sound, 6000, 8, 2.0);
//		}
//
//		/**
//		 * Sets the addition sound of the biome to the given sound configuration.
//		 * @param sound The sound event to set as the addition sound for the biome.
//		 * @param probability The probability that the sound will play every tick.
//		 * @return The biome builder with the modified addition sound.
//		 */
//		public Builder additionSound(SoundEvent sound, double probability) {
//			this.additionSound = new BiomeAdditionsSound(sound, probability);
//			return this;
//		}
//
//		/**
//		 * Sets the game music to be played in the biome to the given sound event.
//		 * @param sound The sound event to set as the game music event for the biome.
//		 * @return The biome builder with the modified music sound.
//		 */
//		public Builder music(SoundEvent sound) {
//			this.music = MusicType.method_27283(sound);
//			return this;
//		}
//
//		/**
//		 * Sets the ambient particles in the biome to the given particle configuration.
//		 * @param particle The particle type to be ambiently generated.
//		 * @param probability The probability the particle will be generated every tick.
//		 * @return The biome builder with the modified ambient particle configuration.
//		 */
//		public Builder particle(ParticleEffect particle, float probability) {
//			this.particle = new BiomeParticleConfig(particle, probability);
//			return this;
//		}
//
//		/**
//		 * Adds a feature entry to the biome.
//		 * @param feature The feature entry to add to the biome.
//		 * @return The biome builder with the added feature entry.
//		 */
//		public Builder feature(FeatureEntry feature) {
//			this.features.add(feature);
//			return this;
//		}
//
//		/**
//		 * Adds a configured feature to the biome.
//		 * @param step The generation step for the feature
//		 * @param feature The configured feature to add to the biome.
//		 * @return The biome builder with the added feature entry.
//		 */
//		public Builder feature(GenerationStep.Feature step, ConfiguredFeature feature) {
//			return feature(new FeatureEntry(step, feature));
//		}
//
//		/**
//		 * Adds a configured structure feature to the biome.
//		 * @param structureFeature The configured structure feature to add to the biome.
//		 * @return The biome builder with the added structure feature.
//		 */
//		public Builder structureFeature(ConfiguredStructureFeature structureFeature) {
//			this.structureFeatures.add(structureFeature);
//			return this;
//		}
//
//		/**
//		 * Adds a spawn entry to the biome.
//		 * @param spawn The spawn entry to add to the biome.
//		 * @return The biome builder with the added spawn entry.
//		 */
//		public Builder spawn(SpawnSettings.SpawnEntry spawn) {
//			this.spawns.add(spawn);
//			return this;
//		}
//
//		/**
//		 * Adds a noise point to the biome.
//		 * @param noise The noise point to add to the biome.
//		 * @return The biome builder with the added noise point.
//		 */
//		public Builder noise(Biome.MixedNoisePoint noise) {
//			this.noisePoints.add(noise);
//			return this;
//		}
//
//		/**
//		 * Constructs a new biome from the biome builder parameters given.
//		 * @return A new biome constructed with the biome builder parameters.
//		 */
//		public Biome build() {
//			return new TerraformNetherBiome(this);
//		}
//
//	}
//
//	/**
//	 * A reusable helper class to easily build nether biomes.
//	 */
//	public static final class Template {
//		/**
//		 * The internal builder properties of the template.
//		 */
//		private final TerraformNetherBiome.Builder builder;
//
//		/**
//		 * Constructs a new template shell from the given builder.
//		 * @param builder The biome builder to construct a shell from.
//		 */
//		public Template(TerraformNetherBiome.Builder builder) {
//			this.builder = builder;
//			builder.template = true;
//		}
//
//		/**
//		 * Returns a copy of internal builder instance from the template shell.
//		 * @return Returns a copy of the internal builder instance.
//		 */
//		public TerraformNetherBiome.Builder builder() {
//			return this.builder.copy();
//		}
//	}
//}
