package com.terraformersmc.terraform.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LogBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.BlockBox;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class FallenLogFeature extends AbstractTreeFeature<TreeFeatureConfig> {
    private final BlockState log;
    private final int minLength, variance;

    public FallenLogFeature(Function<Dynamic<?>, ? extends TreeFeatureConfig> function, boolean notify, BlockState log) {
        this(function, notify, log, 5, 8);
    }

    public FallenLogFeature(Function<Dynamic<?>, ? extends TreeFeatureConfig> function, boolean notify, BlockState log, int minLength, int variance) {
        super(function);
        this.log = log;
        this.minLength = minLength;
        this.variance = variance;
    }

    @Override
    public boolean generate(ModifiableTestableWorld world, Random rand, BlockPos origin,Set<BlockPos> set1, Set<BlockPos> set2, BlockBox boundingBox, TreeFeatureConfig config) {
        // Total log length
        int length = rand.nextInt(variance) + minLength;

        // Axis
        Direction.Axis axis = rand.nextBoolean() ? Direction.Axis.X : Direction.Axis.Z;
        Direction direction = Direction.from(axis, rand.nextBoolean() ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE);

        BlockPos below = origin.down();
        if (!isNaturalDirtOrGrass(world, below)) {
            return false;
        }

        BlockPos.Mutable pos = new BlockPos.Mutable(origin);

        int air = 0;
        for (int i = 0; i < length; i++) {
            pos.setOffset(direction);

            if (!world.testBlockState(pos.setOffset(Direction.DOWN), BlockState::isOpaque)) {
                air++;
            }

            if (!isAirOrLeaves(world, pos.setOffset(Direction.UP))) {
                return false;
            }
        }

        // No floating logs
        if (air * 2 > length) {
            return false;
        }

        pos.set(origin);
        for (int i = 0; i < length; i++) {
            pos.setOffset(direction);

            setBlockState(world, pos, log.with(LogBlock.AXIS, axis), boundingBox);

            pos.setOffset(Direction.DOWN);

            if (isNaturalDirtOrGrass(world, pos)) {
                setBlockState(world, pos, Blocks.DIRT.getDefaultState(), boundingBox);
            }

            pos.setOffset(Direction.UP);
        }

        return true;
    }
}
