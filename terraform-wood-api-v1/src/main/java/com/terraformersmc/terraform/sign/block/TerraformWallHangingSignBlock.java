package com.terraformersmc.terraform.sign.block;

import com.terraformersmc.terraform.sign.TerraformSign;

import net.minecraft.block.WallHangingSignBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;

public class TerraformWallHangingSignBlock extends WallHangingSignBlock implements TerraformSign {
	private final Identifier texture;

	public TerraformWallHangingSignBlock(Identifier texture, Settings settings) {
		super(settings, SignType.OAK);
		this.texture = texture;
	}

	@Override
	public Identifier getTexture() {
		return texture;
	}
}
