package com.terraformersmc.terraform.block;

import com.terraformersmc.terraform.util.TerraformSign;
import net.minecraft.block.WallSignBlock;
import net.minecraft.util.Identifier;

public class TerraformWallSignBlock extends WallSignBlock implements TerraformSign {
    private final Identifier texture;

    public TerraformWallSignBlock(Identifier texture, Settings settings) {
        super(settings);
        this.texture = texture;
    }

    @Override
    public Identifier getTexture() {
        return texture;
    }
}
