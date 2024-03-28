package com.terraformersmc.terraform.sign.api.block;

import com.terraformersmc.terraform.sign.impl.BlockSettingsLock;
import com.terraformersmc.terraform.sign.api.TerraformSign;

import net.minecraft.block.SignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;

public class TerraformSignBlock extends SignBlock implements TerraformSign {
	private final Identifier texture;

	public TerraformSignBlock(Identifier texture, Settings settings) {
		super(WoodType.OAK, BlockSettingsLock.lock(settings));
		this.texture = texture;
	}

	@Override
	public Identifier getTexture() {
		return texture;
	}
}
