package com.terraformersmc.terraform.shapes.impl.layer.transform;

import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.layer.TransformationLayer;

/**
 * @author <Wtoll> Will Toll on 2020-06-07
 * @project Shapes
 */
public class TranslateLayer extends TransformationLayer {

    private final Position translation;

    public TranslateLayer(Position translation) {
        this.translation = translation;
    }

    public static TranslateLayer of(Position translation) {
        return new TranslateLayer(translation);
    }

    @Override
    public Position transform(Position pos) {
        pos.setX(pos.getX() + translation.getX());
        pos.setY(pos.getY() + translation.getY());
        pos.setZ(pos.getZ() + translation.getZ());
        return pos;
    }

    @Override
    public Position inverseTransform(Position pos) {
        pos.setX(pos.getX() - translation.getX());
        pos.setY(pos.getY() - translation.getY());
        pos.setZ(pos.getZ() - translation.getZ());
        return pos;
    }
}
