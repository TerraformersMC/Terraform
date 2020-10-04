//package com.terraformersmc.terraform.biome.builder;
//
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.gen.decorator.CountDecoratorConfig;
//import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
//
//import java.util.*;
//
//import com.terraformersmc.terraform.util.TerraformBiomeSets;
//
//public class TerraformBiome extends Biome {
//	private int grassColor;
//	private int foliageColor;
//	private float spawnChance;
//
//	private TerraformBiome(Biome.Settings biomeSettings, List<SpawnEntry> spawns) {
//		super(biomeSettings);
//		for (SpawnEntry entry : spawns) {
//			this.addSpawn(entry.type.getSpawnGroup(), entry);
//		}
//	}
//
//	public void setGrassAndFoliageColors(int grassColor, int foliageColor) {
//		this.grassColor = grassColor;
//		this.foliageColor = foliageColor;
//	}
//
//	public void setSpawnChance(float spawnChance) {
//		this.spawnChance = spawnChance;
//	}
//
//	@Override
//	public int getGrassColorAt(double x, double y) {
//		if (grassColor == -1) {
//			return super.getGrassColorAt(x, y);
//		}
//
//		return grassColor;
//	}
//
//	@Override
//	public int getFoliageColor() {
//		if (foliageColor == -1) {
//			return super.getFoliageColor();
//		}
//
//		return foliageColor;
//	}
//
//	@Override
//	public float getMaxSpawnChance() {
//		if (spawnChance == -1) {
//			return super.getMaxSpawnChance();
//		}
//
//		return spawnChance;
//	}
//
//		public static TerraformBiomeBuilder builder() {
//		return new TerraformBiomeBuilder();
//	}
//
//}
