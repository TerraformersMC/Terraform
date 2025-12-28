package com.terraformersmc.terraform.shapes.impl.validator;

import com.mojang.datafixers.util.Either;
import com.terraformersmc.terraform.shapes.api.validator.AllMeetValidator;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelSimulatedReader;
import com.terraformersmc.terraform.shapes.api.Position;
import net.minecraft.world.level.block.state.BlockState;

public class AirValidator extends AllMeetValidator {
	private final Either<LevelReader, LevelSimulatedReader> level;

    public AirValidator(LevelReader level) {
		this.level = Either.left(level);
    }

    public AirValidator(LevelSimulatedReader level) {
		this.level = Either.right(level);
    }

    public static AirValidator of(LevelReader level) {
        return new AirValidator(level);
    }

    public static AirValidator of(LevelSimulatedReader level) {
        return new AirValidator(level);
    }

    @Override
    public boolean test(Position position) {
		return level.map(
			reader -> reader.isEmptyBlock(position.toBlockPos()),
			reader -> reader.isStateAtPosition(position.toBlockPos(), BlockState::isAir)
		);
    }
}
