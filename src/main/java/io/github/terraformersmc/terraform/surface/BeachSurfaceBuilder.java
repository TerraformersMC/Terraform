package io.github.terraformersmc.terraform.surface;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public class BeachSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
	protected int seaLevel;
	private DoubleFunction<BlockState> sand;

	protected final BlockState WATER = Blocks.WATER.getDefaultState();

	public BeachSurfaceBuilder(Function<Dynamic<?>, ? extends TernarySurfaceConfig> function, int seaLevel, DoubleFunction<BlockState> sand)
	{
		super(function);

		this.seaLevel = seaLevel;
		this.sand = sand;
	}

	@Override
	public void generate(Random rand, Chunk chunk, Biome biome, int x, int z, int height, double noiseVal, BlockState var9, BlockState var10, int var11, long seed, TernarySurfaceConfig config)
	{
		int localX = x & 15;
		int localZ = z & 15;

		BlockState chosenSand = sand.apply(noiseVal);
		int thickness = (int)(noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);

		int run = 0;
		boolean beach = false;
		boolean underwater = false;

		BlockPos.Mutable pos = new BlockPos.Mutable(localX, 0, localZ);

		for (int y = height; y >= 0; --y)
		{
			pos.set(localX, y, localZ);
			BlockState chunkBlock = chunk.getBlockState(pos);

			if (chunkBlock == STONE && y < 255) {
				BlockState toSet = STONE;

				if (chunk.getBlockState(pos.up()).isAir()) {
					beach = y < seaLevel + 3;
					toSet = beach ? chosenSand : config.getTopMaterial();
				} else if (chunk.getBlockState(pos.up()) == WATER || run < thickness && underwater) {
					underwater = true;

					if(y > seaLevel - 3) {
						beach = true;
						toSet = chosenSand;
					} else {
						toSet = config.getUnderwaterMaterial();
					}
				} else if (y > seaLevel - 3) {
					if(beach) {
						toSet = chosenSand;
					} else if(run < thickness) {
						toSet = config.getUnderMaterial();
					}
				}

				chunk.setBlockState(pos, toSet, false);
				run++;

			} else {
				run = 0;
				beach = false;
				underwater = false;
			}
		}
	}
}
