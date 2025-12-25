package com.terraformersmc.terraform.shapes.impl.layer.transform;

import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Shape;
import com.terraformersmc.terraform.shapes.api.layer.Layer;
import java.util.function.Predicate;
import net.minecraft.util.RandomSource;

public class NoiseTranslateLayer implements Layer {
    private final double magnitude;
    private final RandomSource random;

    public NoiseTranslateLayer(double magnitude, RandomSource random) {
        this.magnitude = magnitude;
        this.random = random;
    }

	/**
	 * @deprecated Use the version accepting Mojang's {@link net.minecraft.util.RandomSource} instead.
	 */
	@Deprecated
    public NoiseTranslateLayer(double magnitude, java.util.Random random) {
		this(magnitude, RandomSource.create(random.nextLong()));
	}

	public static NoiseTranslateLayer of(double magnitude, RandomSource random) {
		return new NoiseTranslateLayer(magnitude, random);
	}

	/**
	 * @deprecated Use the version accepting Mojang's {@link net.minecraft.util.RandomSource} instead.
	 */
	@Deprecated
	public static NoiseTranslateLayer of(double magnitude, java.util.Random random) {
		return new NoiseTranslateLayer(magnitude, random);
	}


    @Override
    public Position modifyMax(Shape shape) {
        Position pos = shape.max();
        pos.setX(pos.getX() + magnitude);
        pos.setY(pos.getY() + magnitude);
        pos.setZ(pos.getZ() + magnitude);
        return pos;
    }

    @Override
    public Position modifyMin(Shape shape) {
        Position pos = shape.min();
        pos.setX(pos.getX() - magnitude);
        pos.setY(pos.getY() - magnitude);
        pos.setZ(pos.getZ() - magnitude);
        return pos;
    }

    @Override
    public Predicate<Position> modifyEquation(Shape shape) {
        return (pos) -> {
            pos.setX(pos.getX() + (random.nextFloat() * this.magnitude) - (random.nextFloat() * this.magnitude));
            pos.setY(pos.getY() + (random.nextFloat() * this.magnitude) - (random.nextFloat() * this.magnitude));
            pos.setZ(pos.getZ() + (random.nextFloat() * this.magnitude) - (random.nextFloat() * this.magnitude));
            return shape.equation().test(pos);
        };
    }
}
