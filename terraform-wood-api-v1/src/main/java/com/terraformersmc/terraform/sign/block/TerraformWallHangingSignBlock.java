package com.terraformersmc.terraform.sign.block;

import com.terraformersmc.terraform.sign.BlockSettingsLock;
import com.terraformersmc.terraform.sign.TerraformHangingSign;

import net.minecraft.block.WallHangingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;

public class TerraformWallHangingSignBlock extends WallHangingSignBlock implements TerraformHangingSign {
	private final Identifier texture;
	private final Identifier guiTexture;

	public TerraformWallHangingSignBlock(Identifier texture, Identifier guiTexture, Settings settings) {
		super(BlockSettingsLock.lock(settings), WoodType.OAK);
		this.texture = texture;
		this.guiTexture = guiTexture;
	}

	@Override
	public Identifier getTexture() {
		return texture;
	}

	@Override
	public Identifier getGuiTexture() {
		return guiTexture;
	}
}
