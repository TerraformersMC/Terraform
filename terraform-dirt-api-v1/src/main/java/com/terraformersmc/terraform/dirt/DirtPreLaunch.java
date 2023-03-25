package com.terraformersmc.terraform.dirt;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class DirtPreLaunch implements PreLaunchEntrypoint {
	public void onPreLaunch() {
		MixinExtrasBootstrap.init();
	}
}
