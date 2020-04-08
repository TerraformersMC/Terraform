package com.terraformersmc.terraform.surface;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class CliffSurfaceBuilder extends SurfaceBuilder<CliffSurfaceConfig> {
	private int seaLevel;
	private SurfaceBuilder<TernarySurfaceConfig> parent;

	public CliffSurfaceBuilder(Function<Dynamic<?>, ? extends CliffSurfaceConfig> function, int seaLevel, SurfaceBuilder<TernarySurfaceConfig> parent) {
		super(function);

		this.seaLevel = seaLevel;
		this.parent = parent;
	}

	@Override
	public void generate(Random rand, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState stone, BlockState water, int var11, long seed, CliffSurfaceConfig config) {
		x &= 15;
		z &= 15;

		height -= 1;

		if (noise > 0.5D && height > seaLevel + 1 && height < seaLevel + 12) {
			if (height > seaLevel + 5) {
				height = seaLevel + 5;
			}

			BlockPos.Mutable pos = new BlockPos.Mutable(x, height, z);

			int basaltLayers = 3;

			if (noise > 1.0D) {
				basaltLayers += 1;
			}

			if (noise > 1.5D) {
				basaltLayers += 1;
			}

			if (height >= seaLevel + 7) {
				basaltLayers += (seaLevel + 5 - height) / 2;
			}

			for (int i = 0; i < basaltLayers; i++) {
				chunk.setBlockState(pos, config.getCliffMaterial(), false);
				pos.move(Direction.UP);
			}

			for (int i = 0; i < 3; i++) {
				chunk.setBlockState(pos, config.getUnderMaterial(), false);
				pos.move(Direction.UP);
			}

			chunk.setBlockState(pos, config.getTopMaterial(), false);
		} else {
			parent.generate(rand, chunk, biome, x, z, height, noise, stone, water, var11, seed, config);
		}
	}
}
