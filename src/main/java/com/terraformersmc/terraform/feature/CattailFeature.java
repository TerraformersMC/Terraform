package com.terraformersmc.terraform.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallSeagrassBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SeagrassFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class CattailFeature extends Feature<SeagrassFeatureConfig> {
	private Block normal;
	private Block tall;

	// TODO: Migrate to feature config
	public CattailFeature(Function<Dynamic<?>, ? extends SeagrassFeatureConfig> function, Block normal, Block tall) {
		super(function);

		this.normal = normal;
		this.tall = tall;
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos origin, SeagrassFeatureConfig config) {
		int placed = 0;

		for (int i = 0; i < config.count; ++i) {
			int x = random.nextInt(8) - random.nextInt(8);
			int z = random.nextInt(8) - random.nextInt(8);
			int y = world.getTopPosition(Heightmap.Type.OCEAN_FLOOR, new BlockPos(origin.getX() + x, 0, origin.getZ() + z)).getY();

			BlockPos candidate = new BlockPos(origin.getX() + x, y, origin.getZ() + z);

			if (world.getBlockState(candidate).getBlock() == Blocks.WATER) {
				boolean tall = random.nextDouble() < config.tallSeagrassProbability;
				BlockState grass = tall ? this.tall.getDefaultState() : this.normal.getDefaultState();

				if (grass.canPlaceAt(world, candidate)) {
					if (tall) {
						BlockState grassTop = grass.with(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER);
						BlockPos upper = candidate.up();

						if (world.getBlockState(upper).getBlock() == Blocks.AIR) {
							world.setBlockState(candidate, grass, 2);
							world.setBlockState(upper, grassTop, 2);
						}
					} else {
						world.setBlockState(candidate, grass, 2);
					}

					placed++;
				}
			}
		}

		return placed > 0;
	}
}
