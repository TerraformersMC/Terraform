package com.terraformersmc.terraform.shapes.api;

import com.terraformersmc.terraform.shapes.impl.ShapesImpl;

/**
 * Shape factories for the API's various shapes.
 * Shapes can be transformed, rotated, and combined; x,y,z refer to initial orientation.
 * <p/>
 * <ul>
 * <li>{@link #rectangle(double, double)}</li>
 * <li>{@link #ellipse(double, double)}</li>
 * <li>{@link #ellipticalPrism(double, double, double)}</li>
 * <li>{@link #rectangularPrism(double, double, double)}</li>
 * <li>{@link #triangularPrism(double, double, double)}</li>
 * <li>{@link #rectangularPyramid(double, double, double)}</li>
 * <li>{@link #ellipticalPyramid(double, double, double)}</li>
 * <li>{@link #ellipsoid(double, double, double)}</li>
 * <li>{@link #hemiEllipsoid(double, double, double)}</li>
 * </ul>
 */
@SuppressWarnings("unused")
public final class Shapes {
	/**
	 * Create a single-block thick rectangular shape with the specified width and depth.
	 *
	 * @param width x width
	 * @param depth z depth
	 * @return rectangular Shape
	 */
    public static Shape rectangle(double width, double depth) {
		return ShapesImpl.rectangle(width, depth);
    }

	/**
	 * Create a single-block thick elliptical shape with the specified width and depth radial lengths.
	 *
	 * @param a x width radial length
	 * @param b z depth radial length
	 * @return elliptical Shape
	 */
    public static Shape ellipse(double a, double b) {
		return ShapesImpl.ellipse(a, b);
    }

	/**
	 * Create an elliptical prism shape with the specified width and depth radial lengths, and height.
	 *
	 * @param a x width radial length
	 * @param b z depth radial length
	 * @param height y height
	 * @return elliptical prism Shape
	 */
    public static Shape ellipticalPrism(double a, double b, double height) {
		return ShapesImpl.ellipticalPrism(a, b, height);
    }

	/**
	 * This accidental misspelling has been replaced with {@linkplain #rectangularPrism}
	 * and will eventually be removed.
	 */
	@Deprecated(since = "17.0.0", forRemoval = true)
	public static Shape rectanglarPrism(double width, double height, double depth) {
		return ShapesImpl.rectangularPrism(width, height, depth);
	}

	/**
	 * Create a rectangular prism shape with the specified width, depth, and height.
	 *
	 * @param width x width
	 * @param height y height
	 * @param depth z depth
	 * @return rectangular prism Shape
	 */
	public static Shape rectangularPrism(double width, double height, double depth) {
		return ShapesImpl.rectangularPrism(width, height, depth);
	}

	/**
	 * Create a triangular prism shape with the specified width, depth, and height.
	 *
	 * @param width x width
	 * @param height y height
	 * @param depth z depth
	 * @return triangular prism Shape
	 */
    public static Shape triangularPrism(double width, double height, double depth) {
        return ShapesImpl.triangularPrism(width, height, depth);
    }

	/**
	 * Create a rectangular pyramid shape with the specified width, depth, and height.
	 *
	 * @param width x width
	 * @param height y height
	 * @param depth z depth
	 * @return rectangular pyramid Shape
	 */
    public static Shape rectangularPyramid(double width, double height, double depth) {
        return ShapesImpl.rectangularPyramid(width, height, depth);
    }

	/**
	 * Create an elliptical pyramid shape with the specified width and depth radial lengths, and height.
	 *
	 * @param a x width radial length
	 * @param b z depth radial length
	 * @param height y height
	 * @return elliptical pyramid Shape
	 */
    public static Shape ellipticalPyramid(double a, double b, double height) {
        return ShapesImpl.ellipticalPyramid(a, b, height);
    }

	/**
	 * Create an ellipsoid shape with the specified width, depth, and height radial lengths.
	 *
	 * @param a x width radial length
	 * @param b z depth radial length
	 * @param c y height radial length
	 * @return ellipsoid Shape
	 */
    public static Shape ellipsoid(double a, double b, double c) {
        return ShapesImpl.ellipsoid(a, b, c);
    }

	/**
	 * Create a hemi-ellipsoid shape with the specified width, depth, and height radial lengths.
	 *
	 * @param a x width radial length
	 * @param b z depth radial length
	 * @param c y height radial length
	 * @return hemi-ellipsoid Shape
	 */
    public static Shape hemiEllipsoid(double a, double b, double c) {
        return ShapesImpl.hemiEllipsoid(a, b, c);
    }
}
