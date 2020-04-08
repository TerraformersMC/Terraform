package com.terraformersmc.terraform.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class FallenLogFeature extends AbstractTreeFeature<FallenLogFeatureConfig> {
	public FallenLogFeature(Function<Dynamic<?>, ? extends FallenLogFeatureConfig> function) {
		super(function);
	}

	@Override
	public boolean generate(ModifiableTestableWorld world, Random rand, BlockPos origin, Set<BlockPos> logs, Set<BlockPos> leaves, BlockBox box, FallenLogFeatureConfig config) {
		// Total log length
		int length = rand.nextInt(config.lengthRandom) + config.baseHeight;

		// Axis
		Direction.Axis axis = rand.nextBoolean() ? Direction.Axis.X : Direction.Axis.Z;
		Direction direction = Direction.from(axis, rand.nextBoolean() ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE);

		BlockPos below = origin.down();
		if (!isNaturalDirtOrGrass(world, below)) {
			return false;
		}

		BlockPos.Mutable pos = origin.mutableCopy();

		int air = 0;
		for (int i = 0; i < length; i++) {
			pos.move(direction);

			if (!world.testBlockState(pos.offset(Direction.DOWN), BlockState::isOpaque)) {
				air++;
			}

			if (!isAirOrLeaves(world, pos.offset(Direction.UP))) {
				return false;
			}
		}

		// No floating logs
		if (air * 2 > length) {
			return false;
		}

		pos.set(origin);
		for (int i = 0; i < length; i++) {
			pos.move(direction);

			BlockState log = config.trunkProvider.getBlockState(rand, pos);

			if (isAirOrLeaves(world, pos) || isReplaceablePlant(world, pos) || isWater(world, pos)) {
				setBlockState(world, pos, log.with(PillarBlock.AXIS, axis), box);
				logs.add(pos.toImmutable());
			}

			pos.move(Direction.DOWN);

			if (isNaturalDirtOrGrass(world, pos)) {
				setToDirt(world, pos);
			}

			pos.move(Direction.UP);
		}

		return true;
	}
}
