package com.terraformersmc.terraform.wood.test;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;

public class TerraformWoodTestClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		TerraformBoatClientHelper.registerModelLayers(TerraformWoodTest.CUSTOM_ID);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, TerraformWoodTest.SIGN_TEXTURE_ID));
	}
}
