package com.terraformersmc.terraform.shapes.impl.filler;

import com.terraformersmc.terraform.shapes.api.Filler;
import com.terraformersmc.terraform.shapes.api.Position;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.block.state.BlockState;

public class RandomSimpleFiller implements Filler {

    private final LevelWriter world;
    private final BlockState state;
    private final int flags;
    private final RandomSource random;
    private final float probability;

    public RandomSimpleFiller(LevelWriter world, BlockState state, int flags, RandomSource random, float probability) {
        this.world = world;
        this.state = state;
        this.flags = flags;
        this.random = random;
        this.probability = probability;
    }

	/**
	 * @deprecated Use the version accepting Mojang's {@link net.minecraft.util.RandomSource} instead.
	 */
	@Deprecated
	public RandomSimpleFiller(LevelWriter world, BlockState state, int flags, java.util.Random random, float probability) {
		this(world, state, flags, RandomSource.create(random.nextLong()), probability);
	}

    public RandomSimpleFiller(LevelWriter world, BlockState state, RandomSource random, float probability) {
        this(world, state, 3, random, probability);
    }

	/**
	 * @deprecated Use the version accepting Mojang's {@link net.minecraft.util.RandomSource} instead.
	 */
	@Deprecated
	public RandomSimpleFiller(LevelWriter world, BlockState state, java.util.Random random, float probability) {
		this(world, state, 3, random, probability);
	}

    public static RandomSimpleFiller of(LevelWriter world, BlockState state, int flags, RandomSource random, float probability) {
        return new RandomSimpleFiller(world, state, flags, random, probability);
    }

	/**
	 * @deprecated Use the version accepting Mojang's {@link net.minecraft.util.RandomSource} instead.
	 */
	@Deprecated
	public static RandomSimpleFiller of(LevelWriter world, BlockState state, int flags, java.util.Random random, float probability) {
		return new RandomSimpleFiller(world, state, flags, random, probability);
	}

	public static RandomSimpleFiller of(LevelWriter world, BlockState state, RandomSource random, float probability) {
        return new RandomSimpleFiller(world, state, random, probability);
    }

	/**
	 * @deprecated Use the version accepting Mojang's {@link net.minecraft.util.RandomSource} instead.
	 */
	@Deprecated
	public static RandomSimpleFiller of(LevelWriter world, BlockState state, java.util.Random random, float probability) {
		return new RandomSimpleFiller(world, state, random, probability);
	}

	@Override
    public void accept(Position position) {
        if (this.random.nextFloat() < this.probability) {
            world.setBlock(position.toBlockPos(), this.state, this.flags);
        }
    }
}
