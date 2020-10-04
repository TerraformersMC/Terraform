package com.terraformersmc.terraform.shapes.impl.layer.transform;

import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.layer.TransformationLayer;

/**
 * @author <Wtoll> Will Toll on 2020-06-07
 * @project Shapes
 */
public class DilateLayer extends TransformationLayer {

    private final Position dilation;

    public DilateLayer(Position dilation) {
        checkValidDilation(dilation);
        this.dilation = dilation;
    }

    public DilateLayer of(Position dilation) {
        return new DilateLayer(dilation);
    }

    @Override
    public Position transform(Position pos) {
        pos.setX(pos.getX() * this.dilation.getX());
        pos.setY(pos.getY() * this.dilation.getY());
        pos.setZ(pos.getZ() * this.dilation.getZ());
        return pos;
    }

    @Override
    public Position inverseTransform(Position pos) {
        pos.setX(pos.getX() / this.dilation.getX());
        pos.setY(pos.getY() / this.dilation.getY());
        pos.setZ(pos.getZ() / this.dilation.getZ());
        return pos;
    }

    private static void checkValidDilation(Position dilation) throws IllegalArgumentException {
        if (dilation.getX() == 0 || dilation.getY() == 0 || dilation.getZ() == 0) throw new IllegalArgumentException("Cannot dilate by 0");
    }
}
