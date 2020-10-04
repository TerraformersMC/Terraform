package com.terraformersmc.terraform.shapes.impl.validator;

import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.validator.AllMeetValidator;
import net.minecraft.block.BlockState;
import net.minecraft.world.TestableWorld;

import java.util.Arrays;
import java.util.List;

/**
 * @author <Wtoll> Will Toll on 2020-06-25
 * @project Shapes
 */
public class SafelistValidator extends AllMeetValidator {

    private final List<BlockState> safeStates;
    private final TestableWorld testableWorld;

    public SafelistValidator(TestableWorld world, List<BlockState> safeStates) {
        this.safeStates = safeStates;
        this.testableWorld = world;
    }

    public SafelistValidator(TestableWorld world, BlockState ...safeStates) {
        this(world, Arrays.asList(safeStates));
    }

    public static SafelistValidator of(TestableWorld world, List<BlockState> safeStates) {
        return new SafelistValidator(world, safeStates);
    }

    public static SafelistValidator of(TestableWorld world, BlockState ...safeStates) {
        return new SafelistValidator(world, safeStates);
    }

    @Override
    public boolean test(Position position) {
        return testableWorld.testBlockState(position.toBlockPos(), (state) -> state.isAir() || safeStates.contains(state));
    }
}
