package com.terraformersmc.terraform.sign.api.block;

import com.terraformersmc.terraform.sign.impl.BlockSettingsLock;
import com.terraformersmc.terraform.sign.api.TerraformSign;

import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;

public class TerraformWallSignBlock extends WallSignBlock implements TerraformSign {
	private final Identifier texture;

	public TerraformWallSignBlock(Identifier texture, WoodType woodType, Settings settings) {
		super(woodType, BlockSettingsLock.lock(settings));
		this.texture = texture;
	}

	public TerraformWallSignBlock(Identifier texture, Settings settings) {
		this(texture, WoodType.OAK, settings);
	}

	@Override
	public Identifier getTexture() {
		return texture;
	}
}
