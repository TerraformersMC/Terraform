package com.terraformersmc.terraform.shapes.impl.filler;

import com.terraformersmc.terraform.shapes.api.Filler;
import com.terraformersmc.terraform.shapes.api.Position;
import net.minecraft.block.BlockState;
import net.minecraft.world.ModifiableWorld;

/**
 * @author <Wtoll> Will Toll on 2020-06-07
 * @project Shapes
 */
public class SimpleFiller implements Filler {

    private final ModifiableWorld world;
    private final BlockState state;
    private final int flags;

    public SimpleFiller(ModifiableWorld world, BlockState state, int flags) {
        this.world = world;
        this.state = state;
        this.flags = flags;
    }

    public SimpleFiller(ModifiableWorld world, BlockState state) {
        this(world, state, 3);
    }

    public static SimpleFiller of(ModifiableWorld world, BlockState state, int flags) {
        return new SimpleFiller(world, state, flags);
    }

    public static SimpleFiller of(ModifiableWorld world, BlockState state) {
        return new SimpleFiller(world, state);
    }

    @Override
    public void accept(Position position) {
        world.setBlockState(position.toBlockPos(), this.state, this.flags);
    }
}
