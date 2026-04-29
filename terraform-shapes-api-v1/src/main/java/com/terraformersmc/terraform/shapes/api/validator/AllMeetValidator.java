package com.terraformersmc.terraform.shapes.api.validator;

import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Shape;

import java.util.function.Predicate;

/**
 * @author <Wtoll> Will Toll on 2020-06-07
 * @project Shapes
 */
@SuppressWarnings("unused")
public abstract class AllMeetValidator implements Validator, Predicate<Position> {
    @Override
    public boolean validate(Shape shape) {
        return shape.stream().allMatch(this);
    }
}
