package com.terraformersmc.terraform.shapes.impl.filler;

import com.terraformersmc.terraform.shapes.api.Filler;
import com.terraformersmc.terraform.shapes.api.Position;
import net.minecraft.block.BlockState;
import net.minecraft.world.ModifiableWorld;

import java.util.Random;

public class RandomSimpleFiller implements Filler {

    private final ModifiableWorld world;
    private final BlockState state;
    private final int flags;
    private final Random random;
    private final float probability;

    public RandomSimpleFiller(ModifiableWorld world, BlockState state, int flags, Random random, float probability) {
        this.world = world;
        this.state = state;
        this.flags = flags;
        this.random = random;
        this.probability = probability;
    }

    public RandomSimpleFiller(ModifiableWorld world, BlockState state, Random random, float probability) {
        this(world, state, 3, random, probability);
    }

    public static RandomSimpleFiller of(ModifiableWorld world, BlockState state, int flags, Random random, float proability) {
        return new RandomSimpleFiller(world, state, flags, random, proability);
    }

    public static RandomSimpleFiller of(ModifiableWorld world, BlockState state, Random random, float proability) {
        return new RandomSimpleFiller(world, state, random, proability);
    }

    @Override
    public void accept(Position position) {
        if (this.random.nextFloat() < this.probability) {
            world.setBlockState(position.toBlockPos(), this.state, this.flags);
        }
    }
}
