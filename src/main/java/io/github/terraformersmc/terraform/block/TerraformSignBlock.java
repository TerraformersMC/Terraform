package io.github.terraformersmc.terraform.block;

import io.github.terraformersmc.terraform.util.TerraformSign;
import net.minecraft.block.SignBlock;
import net.minecraft.util.Identifier;

public class TerraformSignBlock extends SignBlock implements TerraformSign {
    private final Identifier texture;

    public TerraformSignBlock(Identifier texture, Settings settings) {
        super(settings);
        this.texture = texture;
    }

    @Override
    public Identifier getTexture() {
        return texture;
    }
}
