package com.terraformersmc.terraform.biomebuilder;

/**
 * Internal implementation classes only - use {@link TerraformBiomeBuilder#surfaceSlimeSpawns()}.
 */
public class PrivateSlimeSpawnData {
	public interface BuilderExtension {
		void surfaceSlimeSpawns();
	}

	public interface BiomeExtension {
		void setSurfaceSlimeSpawns();
		boolean hasSurfaceSlimeSpawns();
	}
}
