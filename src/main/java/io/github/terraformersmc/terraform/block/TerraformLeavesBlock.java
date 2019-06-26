package io.github.terraformersmc.terraform.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class TerraformLeavesBlock extends LeavesBlock {

    public TerraformLeavesBlock() {
        super(FabricBlockSettings.of(Material.LEAVES).hardness(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).build());
    }

}
