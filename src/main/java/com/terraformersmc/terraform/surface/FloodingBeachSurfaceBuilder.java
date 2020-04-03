package com.terraformersmc.terraform.surface;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public class FloodingBeachSurfaceBuilder extends BeachSurfaceBuilder {
	public FloodingBeachSurfaceBuilder(Function<Dynamic<?>, ? extends TernarySurfaceConfig> function, int seaLevel, DoubleFunction<BlockState> sand) {
		super(function, seaLevel, sand);
	}

	@Override
	public void generate(Random rand, Chunk chunk, Biome biome, int x, int z, int height, double noiseVal, BlockState var9, BlockState var10, int var11, long seed, TernarySurfaceConfig config) {
		BlockPos.Mutable pos = new BlockPos.Mutable(x & 15, this.seaLevel - 1, z & 15);

		while (pos.getY() > 0) {
			if (chunk.getBlockState(pos).isAir() || !chunk.getFluidState(pos).isEmpty()) {
				chunk.setBlockState(pos, WATER, false);
			}

			pos.offset(Direction.DOWN);
		}

		super.generate(rand, chunk, biome, x, z, height, noiseVal, var9, var10, var11, seed, config);
	}
}
