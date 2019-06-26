package io.github.terraformersmc.terraform.block;

import io.github.terraformersmc.terraform.util.TerraformSaplingGenerator;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.SaplingBlock;
import net.minecraft.sound.BlockSoundGroup;

public class TerraformSaplingBlock extends SaplingBlock {

    public TerraformSaplingBlock(TerraformSaplingGenerator generator) {
        super(generator, FabricBlockSettings.of(Material.PLANT).collidable(false).ticksRandomly().hardness(0).sounds(BlockSoundGroup.GRASS).build());
    }

}
