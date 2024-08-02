package com.terraformersmc.terraform.sign.api.block;

import com.terraformersmc.terraform.sign.impl.BlockSettingsLock;
import com.terraformersmc.terraform.sign.api.TerraformSign;

import net.minecraft.block.SignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;

public class TerraformSignBlock extends SignBlock implements TerraformSign {
	private final Identifier texture;

	public TerraformSignBlock(Identifier texture, WoodType woodType, Settings settings) {
		super(woodType, BlockSettingsLock.lock(settings));
		this.texture = texture;
	}

	public TerraformSignBlock(Identifier texture, Settings settings) {
		this(texture, WoodType.OAK, settings);
	}

	@Override
	public Identifier getTexture() {
		return texture;
	}
}
