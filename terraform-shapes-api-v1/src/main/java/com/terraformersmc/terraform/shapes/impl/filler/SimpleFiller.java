package com.terraformersmc.terraform.shapes.impl.filler;

import com.terraformersmc.terraform.shapes.api.Filler;
import com.terraformersmc.terraform.shapes.api.Position;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.block.state.BlockState;

public class SimpleFiller implements Filler {

    private final LevelWriter world;
    private final BlockState state;
    private final int flags;

    public SimpleFiller(LevelWriter world, BlockState state, int flags) {
        this.world = world;
        this.state = state;
        this.flags = flags;
    }

    public SimpleFiller(LevelWriter world, BlockState state) {
        this(world, state, 3);
    }

    public static SimpleFiller of(LevelWriter world, BlockState state, int flags) {
        return new SimpleFiller(world, state, flags);
    }

    public static SimpleFiller of(LevelWriter world, BlockState state) {
        return new SimpleFiller(world, state);
    }

    @Override
    public void accept(Position position) {
        world.setBlock(position.toBlockPos(), this.state, this.flags);
    }
}
