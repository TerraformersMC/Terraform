package com.terraformersmc.terraform.shapes.impl.filler;

import com.terraformersmc.terraform.shapes.api.Filler;
import com.terraformersmc.terraform.shapes.api.Position;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ModifiableWorld;

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

	/**
	 * @deprecated Use the version accepting Mojang's {@link net.minecraft.util.math.random.Random} instead.
	 */
	@Deprecated
	public RandomSimpleFiller(ModifiableWorld world, BlockState state, int flags, java.util.Random random, float probability) {
		this(world, state, flags, Random.create(random.nextLong()), probability);
	}

    public RandomSimpleFiller(ModifiableWorld world, BlockState state, Random random, float probability) {
        this(world, state, 3, random, probability);
    }

	/**
	 * @deprecated Use the version accepting Mojang's {@link net.minecraft.util.math.random.Random} instead.
	 */
	@Deprecated
	public RandomSimpleFiller(ModifiableWorld world, BlockState state, java.util.Random random, float probability) {
		this(world, state, 3, random, probability);
	}

    public static RandomSimpleFiller of(ModifiableWorld world, BlockState state, int flags, Random random, float probability) {
        return new RandomSimpleFiller(world, state, flags, random, probability);
    }

	/**
	 * @deprecated Use the version accepting Mojang's {@link net.minecraft.util.math.random.Random} instead.
	 */
	@Deprecated
	public static RandomSimpleFiller of(ModifiableWorld world, BlockState state, int flags, java.util.Random random, float probability) {
		return new RandomSimpleFiller(world, state, flags, random, probability);
	}

	public static RandomSimpleFiller of(ModifiableWorld world, BlockState state, Random random, float probability) {
        return new RandomSimpleFiller(world, state, random, probability);
    }

	/**
	 * @deprecated Use the version accepting Mojang's {@link net.minecraft.util.math.random.Random} instead.
	 */
	@Deprecated
	public static RandomSimpleFiller of(ModifiableWorld world, BlockState state, java.util.Random random, float probability) {
		return new RandomSimpleFiller(world, state, random, probability);
	}

	@Override
    public void accept(Position position) {
        if (this.random.nextFloat() < this.probability) {
            world.setBlockState(position.toBlockPos(), this.state, this.flags);
        }
    }
}
