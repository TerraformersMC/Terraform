package com.terraformersmc.terraform.sign.block;

import com.terraformersmc.terraform.sign.TerraformSign;

import net.minecraft.block.SignBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;

public class TerraformSignBlock extends SignBlock implements TerraformSign {
	private final Identifier texture;

	public TerraformSignBlock(Identifier texture, Settings settings) {
		super(settings, SignType.OAK);
		this.texture = texture;
	}

	@Override
	public Identifier getTexture() {
		return texture;
	}
}
