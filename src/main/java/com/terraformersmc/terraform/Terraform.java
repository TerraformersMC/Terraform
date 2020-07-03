package com.terraformersmc.terraform;

import com.terraformersmc.terraform.command.MapBiomesCommand;
import com.terraformersmc.terraform.util.NoiseCollisionChecker;
import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

/**
 * This is the root class of the mod that is loaded by fabric at run time.
 * Terraform#onInitialize()
 */
public class Terraform implements ModInitializer {

	/**
	 * A static string representing the namespace of all content in the mod
	 */
	public static final String MODID = "terraform";

	/**
	 * A logger under the terraform name
	 */
	public static final Logger LOGGER = Logger.getLogger("Terraform");

	/**
	 * The main initialization method used as an entry point for fabric.
	 */
	@Override
	public void onInitialize() {
		MapBiomesCommand.register();
		NoiseCollisionChecker.onInitialize();
	}
}
