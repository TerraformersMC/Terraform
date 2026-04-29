package com.terraformersmc.terraform.shapes.api.validator;

import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Shape;
import com.terraformersmc.terraform.shapes.impl.validator.AirValidator;
import com.terraformersmc.terraform.shapes.impl.validator.SafelistValidator;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.function.Predicate;

/**
 * Validators are used to check whether specified blocks or entire shapes,
 * at particular positions, meet defined requirements.
 * <p/>
 * <ul>
 * <li>{@link #allMeet(Predicate)}</li>
 * <li>{@link #air(LevelReader)}</li>
 * <li>{@link #air(LevelSimulatedReader)}</li>
 * <li>{@link #safelist(LevelSimulatedReader, List)}</li>
 * <li>{@link #safelist(LevelSimulatedReader, BlockState...)}</li>
 * </ul>
 * <p/>
 * @author <Wtoll> Will Toll on 2020-06-07
 * @project Shapes
 */
@SuppressWarnings("unused")
public interface Validator {
    boolean validate(Shape shape);


	/**
	 * Validator for checking entire shapes.
	 *
	 * @param predicate predicate to be tested
	 * @return new AllMeetValidator
	 */
	static AllMeetValidator allMeet(Predicate<Position> predicate) {
		return new AllMeetValidator() {
			@Override
			public boolean test(Position position) {
				return predicate.test(position);
			}
		};
	}

	/**
	 * Validator for checking whether postion or shape is entirely empty air.
	 *
	 * @param level target level
	 * @return new AirValidator
	 */
	static AllMeetValidator air(LevelReader level) {
		return new AirValidator(level);
	}

	/**
	 * Validator for checking whether postion or shape is entirely empty air.
	 *
	 * @param level target simulated level
	 * @return new AirValidator
	 */
	static AllMeetValidator air(LevelSimulatedReader level) {
		return new AirValidator(level);
	}

	/**
	 * Validator for checking whether position or shape is entirely comprised of listed blocks.
	 *
	 * @param level target level
	 * @param safeStates list of matching block states
	 * @return new SafeListValidator
	 */
	static AllMeetValidator safelist(LevelSimulatedReader level, List<BlockState> safeStates) {
		return new SafelistValidator(level, safeStates);
	}

	/**
	 * Validator for checking whether position or shape is entirely comprised of listed blocks.
	 *
	 * @param level target level
	 * @param safeStates one or more matching block states
	 * @return new SafeListValidator
	 */
	static AllMeetValidator safelist(LevelSimulatedReader level, BlockState ...safeStates) {
		return new SafelistValidator(level, safeStates);
	}
}
