package com.terraformersmc.terraform.shapes.impl.validator;

import com.terraformersmc.terraform.shapes.api.Position;

import java.util.Arrays;
import java.util.List;

import com.terraformersmc.terraform.shapes.api.validator.AllMeetValidator;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;

public class SafelistValidator extends AllMeetValidator {
    private final List<BlockState> safeStates;
    private final LevelSimulatedReader level;

    public SafelistValidator(LevelSimulatedReader level, List<BlockState> safeStates) {
        this.safeStates = safeStates;
        this.level = level;
    }

    public SafelistValidator(LevelSimulatedReader level, BlockState ...safeStates) {
        this(level, Arrays.asList(safeStates));
    }

    @Override
    public boolean test(Position position) {
        return level.isStateAtPosition(position.toBlockPos(), state -> state.isAir() || safeStates.contains(state));
    }
}
