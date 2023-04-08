package com.terraformersmc.terraform.wood;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class WoodPreLaunch implements PreLaunchEntrypoint {
	public void onPreLaunch() {
		MixinExtrasBootstrap.init();
	}
}
