package com.terraformersmc.terraform.shapes.impl.layer.pathfinder;

import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Shape;
import com.terraformersmc.terraform.shapes.api.layer.PathfinderLayer;

import java.util.function.Predicate;

/**
 * @author <Wtoll> Will Toll on 2020-06-07
 * @project Shapes
 */
public class IntersectLayer extends PathfinderLayer {

    private final Shape shape;

    public IntersectLayer(Shape shape) {
        this.shape = shape;
    }

    @Override
    public Position modifyMax(Shape shape) {
        return Position.of(Math.min(shape.max().getX(), this.shape.max().getX()), Math.min(shape.max().getY(), this.shape.max().getY()), Math.min(shape.max().getZ(), this.shape.max().getZ()));
    }

    @Override
    public Position modifyMin(Shape shape) {
        return Position.of(Math.max(shape.min().getX(), this.shape.min().getX()), Math.max(shape.min().getY(), this.shape.min().getY()), Math.max(shape.min().getZ(), this.shape.min().getZ()));
    }

    @Override
    public Predicate<Position> modifyEquation(Shape shape) {
        return (pos) -> shape.equation().test(pos.copy()) && this.shape.equation().test(pos.copy());
    }
}
