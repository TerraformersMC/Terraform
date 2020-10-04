package com.terraformersmc.terraform.shapes.api.layer;

import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Shape;

import java.util.function.Predicate;

/**
 * @author <Wtoll> Will Toll on 2020-06-07
 * @project Shapes
 */
public interface Layer {
    Position modifyMax(Shape shape);

    Position modifyMin(Shape shape);

    Predicate<Position> modifyEquation(Shape shape);
}
