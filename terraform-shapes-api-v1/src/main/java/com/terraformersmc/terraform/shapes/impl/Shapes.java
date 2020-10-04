package com.terraformersmc.terraform.shapes.impl;

import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Shape;

/**
 * @author <Wtoll> Will Toll on 2020-06-07
 * @project Shapes
 */
public class Shapes {

    public static Shape rectangle(double w, double d) {
        return Shape.of(
                (pos) -> pos.getY() > 0 && pos.getY() <= 1,
                Position.of(w/2, 0, d/2),
                Position.of(-w/2, 0, -d/2)
        );
    }

    public static Shape ellipse(double a, double b) {
        return Shape.of(
                (pos) -> ((pos.getX() * pos.getX())/(a * a)) + ((pos.getZ() * pos.getZ())/(b * b)) < 1 && pos.getY() > 0 && pos.getY() < 1,
                Position.of(a, 0, b),
                Position.of(-a, 0, -b)
        );
    }




    public static Shape ellipticalPrism(double a, double b, double height) {
        return Shape.of(
                (pos) -> ((pos.getX() * pos.getX())/(a * a)) + ((pos.getZ() * pos.getZ())/(b * b)) < 1 && pos.getY() > -height/2 && pos.getY() < height/2,
                Position.of(a, height/2, b),
                Position.of(-a, -height/2, -b)
        );
    }

    public static Shape rectanglarPrism(double width, double height, double depth) {
        return Shape.of(
                (pos) -> pos.getX() > -width/2 && pos.getX() < width/2 && pos.getY() > -height/2 && pos.getY() < height/2 && pos.getZ() > -depth/2 && pos.getZ() < depth/2,
                Position.of(width/2, height/2, depth/2),
                Position.of(-width/2, -height/2, -depth/2)
        );
    }

    public static Shape triangularPrism(double width, double height, double depth) {
        return Shape.of(
                (pos) -> pos.getX() > -width/2 && pos.getX() < width/2 && pos.getY() > -height/2 && pos.getY() < height/2 && pos.getZ() > -(depth*(1-((pos.getY()+height/2)/height)))/2 && pos.getZ() < (depth*(1-((pos.getY()+height/2)/height)))/2,
                Position.of(width/2, height/2, depth/2),
                Position.of(-width/2, -height/2, -depth/2)
        );
    }





    public static Shape rectangularPyramid(double width, double height, double depth) {
        return Shape.of(
                (pos) -> pos.getX() > -(width*(1-(pos.getY()/height)))/2 && pos.getX() < (width*(1-(pos.getY()/height)))/2 && pos.getY() > 0 && pos.getY() < height && pos.getZ() > -(depth*(1-(pos.getY()/height)))/2 && pos.getZ() < (depth*(1-(pos.getY()/height)))/2,
                Position.of(width/2, height, depth/2),
                Position.of(-width/2, 0, -depth/2)
        );
    }

    public static Shape ellipticalPyramid(double a, double b, double height) {
        return Shape.of(
                (pos) -> ((pos.getX() * pos.getX())/((a*(1-(pos.getY()/height))) * (a*(1-(pos.getY()/height))))) + ((pos.getZ() * pos.getZ())/((b*(1-(pos.getY()/height))) * (b*(1-(pos.getY()/height))))) < 1 && pos.getY() > 0 && pos.getY() < height,
                Position.of(a, height, b),
                Position.of(-a, 0, -b)
        );
    }


    public static Shape ellipsoid(double a, double b, double c) {
        return Shape.of(
                (pos) -> ((pos.getX() * pos.getX())/(a * a)) + ((pos.getZ() * pos.getZ())/(b * b)) + ((pos.getY() * pos.getY())/(c * c)) < 1,
                Position.of(a, c, b),
                Position.of(-a, -c, -b)
        );
    }

    public static Shape hemiEllipsoid(double a, double b, double c) {
        return Shape.of(
                (pos) -> ((pos.getX() * pos.getX())/(a * a)) + ((pos.getZ() * pos.getZ())/(b * b)) + ((pos.getY() * pos.getY())/(c * c)) < 1 && pos.getY() > 0,
                Position.of(a, c, b),
                Position.of(-a, 0, -b)
        );
    }

}
