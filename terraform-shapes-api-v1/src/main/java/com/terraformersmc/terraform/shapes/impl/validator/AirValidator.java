package com.terraformersmc.terraform.shapes.impl.validator;

import com.terraformersmc.terraform.shapes.api.validator.AllMeetValidator;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelSimulatedReader;
import com.terraformersmc.terraform.shapes.api.Position;

public class AirValidator extends AllMeetValidator {

    private final LevelReader worldView;
    private final LevelSimulatedReader testableWorld;

    public AirValidator(LevelReader world) {
        this.worldView = world;
        this.testableWorld = null;
    }

    public AirValidator(LevelSimulatedReader world) {
        this.worldView = null;
        this.testableWorld = world;
    }

    public static AirValidator of(LevelReader world) {
        return new AirValidator(world);
    }

    public static AirValidator of(LevelSimulatedReader world) {
        return new AirValidator(world);
    }

    @Override
    public boolean test(Position position) {
        if (worldView != null) {
            return worldView.isEmptyBlock(position.toBlockPos());
        } else if (testableWorld != null) {
            return testableWorld.isStateAtPosition(position.toBlockPos(), (state) -> state.isAir());
        } else {
            return false;
        }
    }
}
