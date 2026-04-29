package com.terraformersmc.terraform.shapes.api.layer;

import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Quaternion;
import com.terraformersmc.terraform.shapes.api.Shape;
import com.terraformersmc.terraform.shapes.impl.layer.pathfinder.AddLayer;
import com.terraformersmc.terraform.shapes.impl.layer.pathfinder.ExcludeLayer;
import com.terraformersmc.terraform.shapes.impl.layer.pathfinder.IntersectLayer;
import com.terraformersmc.terraform.shapes.impl.layer.pathfinder.SubtractLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.DilateLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.NoiseTranslateLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.RotateLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.TranslateLayer;
import net.minecraft.util.RandomSource;

import java.util.function.Predicate;

/**
 * Layers modify shapes by combining them with other shapes or
 * applying various transformations to them.
 * <p/>
 * Pathfinder layers:
 * <ul>
 * <li>{@link #add(Shape)}</li>
 * <li>{@link #exclude(Shape)}</li>
 * <li>{@link #intersect(Shape)}</li>
 * <li>{@link #subtract(Shape)}</li>
 * </ul>
 * <p/>
 * Transformation layers:
 * <ul>
 * <li>{@link #dilate(Position)}</li>
 * <li>{@link #noiseTranslate(double, RandomSource)}</li>
 * <li>{@link #rotate(Quaternion)}</li>
 * <li>{@link #translate(Position)}</li>
 * </ul>
 * <p/>
 */
@SuppressWarnings("unused")
public interface Layer {
    Position modifyMax(Shape shape);

    Position modifyMin(Shape shape);

    Predicate<Position> modifyEquation(Shape shape);


	/**
	 * Union of the volume of the shape stack with the specified shape.
	 * (This is the entire volume occupied by either or both shapes.)
	 *
	 * @param shape Shape to be added
	 * @return new AddLayer
	 */
	static PathfinderLayer add(Shape shape) {
		return new AddLayer(shape);
	}

	/**
	 * Symmetric difference of the shape stack and the specified shape.
	 * (This is the non-overlapping volumes from both shapes.)
	 *
	 * @param shape Shape to be excluded
	 * @return new ExcludeLayer
	 */
	static PathfinderLayer exclude(Shape shape) {
		return new ExcludeLayer(shape);
	}

	/**
	 * Selects the intersection of the shape stack with the specified shape.
	 * (This is the volume of the shape stack also included in the shape.)
	 *
	 * @param shape Shape to be intersected
	 * @return new IntersectLayer
	 */
	static PathfinderLayer intersect(Shape shape) {
		return new IntersectLayer(shape);
	}

	/**
	 * Exclude the volume of the specified shape from the shape stack.
	 * (This is the volume of the shape stack not included in the shape.)
	 *
	 * @param shape Shape to be excluded
	 * @return new SubtractLayer
	 */
	static PathfinderLayer subtract(Shape shape) {
		return new SubtractLayer(shape);
	}


	/**
	 * Dilates the shape stack in 3D around the specified position.
	 *
	 * @param dilation Position around which to dilate
	 * @return new DilateLayer
	 */
	static TransformationLayer dilate(Position dilation) {
		return new DilateLayer(dilation);
	}

	/**
	 * Translates points in the shape stack in 3D by a center-biased
	 * random amount between positive and negative (magnitude * sqrt(3)).
	 *
	 * @param magnitude Maximum magnitude of the translation vectors
	 * @param random Random source
	 * @return new NoiseTranslationLayer
	 */
	static Layer noiseTranslate(double magnitude, RandomSource random) {
		return new NoiseTranslateLayer(magnitude, random);
	}

	/**
	 * Rotates the shape stack by the rotation specified in the quaternion.
	 *
	 * @param rotation Rotation specification quaternion
	 * @return new RotateLayer
	 */
	static Layer rotate(Quaternion rotation) {
		return new RotateLayer(rotation);
	}

	/**
	 * Translates the shape stack in 3D by the x, y, and z vectors specified in the position.
	 *
	 * @param translation Translation specification position
	 * @return new TranslateLayer
	 */
	static TransformationLayer translate(Position translation) {
		return new TranslateLayer(translation);
	}
}
