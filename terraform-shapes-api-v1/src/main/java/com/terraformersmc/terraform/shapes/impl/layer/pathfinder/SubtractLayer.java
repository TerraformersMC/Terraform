package com.terraformersmc.terraform.shapes.impl.layer.pathfinder;

import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Shape;
import com.terraformersmc.terraform.shapes.api.layer.PathfinderLayer;

import java.util.function.Predicate;

/**
 * @author <Wtoll> Will Toll on 2020-06-07
 * @project Shapes
 */
public class SubtractLayer extends PathfinderLayer {

    private final Shape shape;

    public SubtractLayer(Shape shape) {
        this.shape = shape;
    }

    @Override
    public Position modifyMax(Shape shape) {
        return shape.max();
    }

    @Override
    public Position modifyMin(Shape shape) {
        return shape.min();
    }

    @Override
    public Predicate<Position> modifyEquation(Shape shape) {
        return (pos) -> shape.equation().test(pos.copy()) && !this.shape.equation().test(pos.copy());
    }
}
