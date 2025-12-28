package com.terraformersmc.terraform.shapes.impl.filler;

import com.terraformersmc.terraform.shapes.api.Filler;
import com.terraformersmc.terraform.shapes.api.Position;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.block.state.BlockState;

public class RandomSimpleFiller implements Filler {
    private final LevelWriter level;
    private final BlockState state;
    private final int flags;
    private final RandomSource random;
    private final float probability;

    public RandomSimpleFiller(LevelWriter level, BlockState state, int flags, RandomSource random, float probability) {
        this.level = level;
        this.state = state;
        this.flags = flags;
        this.random = random;
        this.probability = probability;
    }

    public RandomSimpleFiller(LevelWriter level, BlockState state, RandomSource random, float probability) {
        this(level, state, 3, random, probability);
    }

    public static RandomSimpleFiller of(LevelWriter level, BlockState state, int flags, RandomSource random, float probability) {
        return new RandomSimpleFiller(level, state, flags, random, probability);
    }

	public static RandomSimpleFiller of(LevelWriter level, BlockState state, RandomSource random, float probability) {
        return new RandomSimpleFiller(level, state, random, probability);
    }

	@Override
    public void accept(Position position) {
        if (this.random.nextFloat() < this.probability) {
            level.setBlock(position.toBlockPos(), this.state, this.flags);
        }
    }
}
