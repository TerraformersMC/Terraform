package com.terraformersmc.terraform.shapes.impl.validator;

import com.terraformersmc.terraform.shapes.api.validator.AllMeetValidator;
import com.terraformersmc.terraform.shapes.api.Position;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.WorldView;

public class AirValidator extends AllMeetValidator {

    private final WorldView worldView;
    private final TestableWorld testableWorld;

    public AirValidator(WorldView world) {
        this.worldView = world;
        this.testableWorld = null;
    }

    public AirValidator(TestableWorld world) {
        this.worldView = null;
        this.testableWorld = world;
    }

    public static AirValidator of(WorldView world) {
        return new AirValidator(world);
    }

    public static AirValidator of(TestableWorld world) {
        return new AirValidator(world);
    }

    @Override
    public boolean test(Position position) {
        if (worldView != null) {
            return worldView.isAir(position.toBlockPos());
        } else if (testableWorld != null) {
            return testableWorld.testBlockState(position.toBlockPos(), (state) -> state.isAir());
        } else {
            return false;
        }
    }
}
