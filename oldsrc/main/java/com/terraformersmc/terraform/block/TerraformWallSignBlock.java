package com.terraformersmc.terraform.block;

import com.terraformersmc.terraform.util.TerraformSign;

import net.minecraft.block.WallSignBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;

public class TerraformWallSignBlock extends WallSignBlock implements TerraformSign {
    private final Identifier texture;

    public TerraformWallSignBlock(Identifier texture, Settings settings) {
        super(settings, SignType.OAK); //TODO: take a look at this again
        this.texture = texture;
    }

    @Override
    public Identifier getTexture() {
        return texture;
    }
}
