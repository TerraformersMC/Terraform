package com.terraformersmc.terraform.shapes.api.layer;

import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Shape;

import java.util.function.Predicate;

public interface Layer {
    Position modifyMax(Shape shape);

    Position modifyMin(Shape shape);

    Predicate<Position> modifyEquation(Shape shape);
}
