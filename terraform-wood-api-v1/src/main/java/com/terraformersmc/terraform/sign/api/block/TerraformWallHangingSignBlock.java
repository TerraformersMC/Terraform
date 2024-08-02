package com.terraformersmc.terraform.sign.api.block;

import com.terraformersmc.terraform.sign.impl.BlockSettingsLock;
import com.terraformersmc.terraform.sign.api.TerraformHangingSign;

import net.minecraft.block.WallHangingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;

public class TerraformWallHangingSignBlock extends WallHangingSignBlock implements TerraformHangingSign {
	private final Identifier texture;
	private final Identifier guiTexture;

	public TerraformWallHangingSignBlock(Identifier texture, Identifier guiTexture, WoodType woodType, Settings settings) {
		super(woodType, BlockSettingsLock.lock(settings));
		this.texture = texture;
		this.guiTexture = guiTexture;
	}

	public TerraformWallHangingSignBlock(Identifier texture, Identifier guiTexture, Settings settings) {
		this(texture, guiTexture, WoodType.OAK, settings);
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
