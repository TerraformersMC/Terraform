package com.terraformersmc.terraform.biome.builder;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.BiomeParticleConfig;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;

public class BuilderBiomeSettings extends Biome.Settings implements Cloneable {
	private ConfiguredSurfaceBuilder<?> surfaceBuilder;
	private Biome.Precipitation precipitation;
	private Biome.Category category;
	private Float depth;
	private Float scale;
	private Float temperature;
	private Float downfall;
	private Integer waterColor;
	private Integer waterFogColor;
	private Integer fogColor;
	private BiomeParticleConfig particleConfig;
	private List<Biome.MixedNoisePoint> noisePoints = new ArrayList<>();
	private String parent;

	public BuilderBiomeSettings() {
		super();
	}

	public BuilderBiomeSettings(BuilderBiomeSettings existing) {
		if (existing.surfaceBuilder != null) {
			this.surfaceBuilder(existing.surfaceBuilder);
		}

		if (existing.precipitation != null) {
			this.precipitation(existing.precipitation);
		}

		if (existing.category != null) {
			this.category(existing.category);
		}

		if (existing.depth != null) {
			this.depth(existing.depth);
		}

		if (existing.scale != null) {
			this.scale(existing.scale);
		}

		if (existing.temperature != null) {
			this.temperature(existing.temperature);
		}

		if (existing.downfall != null) {
			this.downfall(existing.downfall);
		}

		if (existing.waterColor != null) {
			this.waterColor(existing.waterColor);
		}

		if (existing.waterFogColor != null) {
			this.waterFogColor(existing.waterFogColor);
		}

		if (existing.fogColor != null) {
			this.fogColor(existing.fogColor);
		}

		if (existing.noisePoints != null) {
			existing.noisePoints.forEach(this::noisePoint);
		}

		if (existing.parent != null) {
			this.parent(existing.parent);
		}
	}

	@Override
	public <SC extends SurfaceConfig> BuilderBiomeSettings configureSurfaceBuilder(SurfaceBuilder<SC> builder, SC config) {
		this.surfaceBuilder = new ConfiguredSurfaceBuilder<>(builder, config);
		super.configureSurfaceBuilder(builder, config);

		return this;
	}

	@Override
	public BuilderBiomeSettings surfaceBuilder(ConfiguredSurfaceBuilder<?> surfaceBuilder) {
		this.surfaceBuilder = surfaceBuilder;
		super.surfaceBuilder(surfaceBuilder);

		return this;
	}

	@Override
	public BuilderBiomeSettings precipitation(Biome.Precipitation precipitation) {
		this.precipitation = precipitation;
		super.precipitation(precipitation);

		return this;
	}

	@Override
	public BuilderBiomeSettings category(Biome.Category category) {
		this.category = category;
		super.category(category);

		return this;
	}

	@Override
	public BuilderBiomeSettings depth(float depth) {
		this.depth = depth;
		super.depth(depth);

		return this;
	}

	@Override
	public BuilderBiomeSettings scale(float scale) {
		this.scale = scale;
		super.scale(scale);

		return this;
	}

	@Override
	public BuilderBiomeSettings temperature(float temperature) {
		this.temperature = temperature;
		super.temperature(temperature);

		return this;
	}

	@Override
	public BuilderBiomeSettings downfall(float downfall) {
		this.downfall = downfall;
		super.downfall(downfall);

		return this;
	}

	@Deprecated
	public BuilderBiomeSettings waterColor(int color) {
		this.waterColor = color;
		updateSpecialEffects();
		return this;
	}

	@Deprecated
	public BuilderBiomeSettings waterFogColor(int color) {
		this.waterFogColor = color;
		updateSpecialEffects();
		return this;
	}

	@Deprecated
	public BuilderBiomeSettings fogColor(int color) {
		this.fogColor = color;
		updateSpecialEffects();
		return this;
	}

	@Deprecated
	public BuilderBiomeSettings particleConfig(BiomeParticleConfig config) {
		this.particleConfig = config;
		updateSpecialEffects();
		return this;
	}

	@Deprecated
	public BuilderBiomeSettings noisePoint(Biome.MixedNoisePoint point) {
		this.noisePoints.add(point);
		super.noises(noisePoints);
		return this;
	}

	@Deprecated
	private void updateSpecialEffects() {
		if (waterColor != null && waterFogColor != null) {
			BiomeEffects.Builder builder = new BiomeEffects.Builder();
			builder.waterColor(waterColor); // water color
			builder.waterFogColor(waterFogColor); // water fog color
			builder.fogColor(fogColor); // fog color
			builder.particleConfig(particleConfig); // particle effects
			effects(builder.build()); // build & apply
		}
	}

	@Override
	public BuilderBiomeSettings parent(String parent) {
		this.parent = parent;
		super.parent(parent);

		return this;
	}
}
