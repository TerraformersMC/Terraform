package com.terraformersmc.terraform.shapes.impl.validator;

import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.validator.AllMeetValidator;
import java.util.Arrays;
import java.util.List;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;

public class SafelistValidator extends AllMeetValidator {

    private final List<BlockState> safeStates;
    private final LevelSimulatedReader testableWorld;

    public SafelistValidator(LevelSimulatedReader world, List<BlockState> safeStates) {
        this.safeStates = safeStates;
        this.testableWorld = world;
    }

    public SafelistValidator(LevelSimulatedReader world, BlockState ...safeStates) {
        this(world, Arrays.asList(safeStates));
    }

    public static SafelistValidator of(LevelSimulatedReader world, List<BlockState> safeStates) {
        return new SafelistValidator(world, safeStates);
    }

    public static SafelistValidator of(LevelSimulatedReader world, BlockState ...safeStates) {
        return new SafelistValidator(world, safeStates);
    }

    @Override
    public boolean test(Position position) {
        return testableWorld.isStateAtPosition(position.toBlockPos(), (state) -> state.isAir() || safeStates.contains(state));
    }
}
