package com.terraformersmc.terraform.wood.test;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;

import net.fabricmc.api.ClientModInitializer;

public class TerraformWoodTestClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		TerraformBoatClientHelper.registerModelLayers(TerraformWoodTest.CUSTOM_BOAT_ID, false);
		TerraformBoatClientHelper.registerModelLayers(TerraformWoodTest.CUSTOM_RAFT_ID, true);
	}
}
