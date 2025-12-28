package com.terraformersmc.terraform.shapes.impl.validator;

import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.validator.AllMeetValidator;
import java.util.Arrays;
import java.util.List;
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

    public static SafelistValidator of(LevelSimulatedReader level, List<BlockState> safeStates) {
        return new SafelistValidator(level, safeStates);
    }

    public static SafelistValidator of(LevelSimulatedReader level, BlockState ...safeStates) {
        return new SafelistValidator(level, safeStates);
    }

    @Override
    public boolean test(Position position) {
        return level.isStateAtPosition(position.toBlockPos(), state -> state.isAir() || safeStates.contains(state));
    }
}
