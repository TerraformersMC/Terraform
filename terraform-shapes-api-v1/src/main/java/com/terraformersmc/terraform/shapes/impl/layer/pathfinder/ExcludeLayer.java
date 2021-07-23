package com.terraformersmc.terraform.shapes.impl.layer.pathfinder;

import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Shape;
import com.terraformersmc.terraform.shapes.api.layer.PathfinderLayer;

import java.util.function.Predicate;

public class ExcludeLayer extends PathfinderLayer {

    private final Shape shape;

    public ExcludeLayer(Shape shape) {
        this.shape = shape;
    }

    @Override
    public Position modifyMax(Shape shape) {
        return Position.of(Math.max(shape.max().getX(), this.shape.max().getX()), Math.max(shape.max().getY(), this.shape.max().getY()), Math.max(shape.max().getZ(), this.shape.max().getZ()));
    }

    @Override
    public Position modifyMin(Shape shape) {
        return Position.of(Math.min(shape.min().getX(), this.shape.min().getX()), Math.min(shape.min().getY(), this.shape.min().getY()), Math.min(shape.min().getZ(), this.shape.min().getZ()));
    }

    @Override
    public Predicate<Position> modifyEquation(Shape shape) {
        return (pos) -> shape.equation().test(pos.copy()) ^ this.shape.equation().test(pos.copy());
    }
}
