package com.terraformersmc.terraform.shapes.impl.filler;

import com.terraformersmc.terraform.shapes.api.Filler;
import com.terraformersmc.terraform.shapes.api.Position;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.block.state.BlockState;

public class SimpleFiller implements Filler {
    private final LevelWriter level;
    private final BlockState state;
    private final int flags;

    public SimpleFiller(LevelWriter level, BlockState state, int flags) {
        this.level = level;
        this.state = state;
        this.flags = flags;
    }

    public SimpleFiller(LevelWriter level, BlockState state) {
        this(level, state, 3);
    }

    public static SimpleFiller of(LevelWriter level, BlockState state, int flags) {
        return new SimpleFiller(level, state, flags);
    }

    public static SimpleFiller of(LevelWriter level, BlockState state) {
        return new SimpleFiller(level, state);
    }

    @Override
    public void accept(Position position) {
        level.setBlock(position.toBlockPos(), this.state, this.flags);
    }
}
