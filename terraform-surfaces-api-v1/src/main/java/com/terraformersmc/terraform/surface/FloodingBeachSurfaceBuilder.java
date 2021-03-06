package com.terraformersmc.terraform.surface;

import java.util.Random;
import java.util.function.DoubleFunction;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class FloodingBeachSurfaceBuilder extends BeachSurfaceBuilder {
	public FloodingBeachSurfaceBuilder(Codec<TernarySurfaceConfig> codec, int seaLevel, DoubleFunction<BlockState> sand) {
		super(codec, seaLevel, sand);
	}

	@Override
	public void generate(Random rand, Chunk chunk, Biome biome, int x, int z, int height, double noiseVal, BlockState var9, BlockState var10, int var11, long seed, TernarySurfaceConfig config) {
		BlockPos.Mutable pos = new BlockPos.Mutable(x & 15, this.seaLevel - 1, z & 15);

		while (pos.getY() > 0) {
			if (chunk.getBlockState(pos).isAir() || !chunk.getFluidState(pos).isEmpty()) {
				chunk.setBlockState(pos, WATER, false);
			}

			pos.move(Direction.DOWN);
		}

		super.generate(rand, chunk, biome, x, z, height, noiseVal, var9, var10, var11, seed, config);
	}
}
