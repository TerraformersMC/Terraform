package com.terraformersmc.terraform.sign.block;

import com.terraformersmc.terraform.sign.BlockSettingsLock;
import com.terraformersmc.terraform.sign.TerraformSign;

import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;

public class TerraformWallSignBlock extends WallSignBlock implements TerraformSign {
	private final Identifier texture;

	public TerraformWallSignBlock(Identifier texture, Settings settings) {
		super(WoodType.OAK, BlockSettingsLock.lock(settings)); // TODO: take a look at this again
		this.texture = texture;
	}

	@Override
	public Identifier getTexture() {
		return texture;
	}
}
