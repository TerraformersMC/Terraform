package com.terraformersmc.terraform.wood.block;

import net.minecraft.block.PressurePlateBlock;

/**
 * A simple wrapper around the parent PressurePlateBlock class allowing access to the constructor.
 * @deprecated Use the access widener provided by the {@code fabric-transitive-access-wideners-v1} module
 */
@Deprecated(forRemoval = true)
public class TerraformPressurePlateBlock extends PressurePlateBlock {
	public TerraformPressurePlateBlock(Settings settings) {
		super(ActivationRule.EVERYTHING, settings);
	}

	public TerraformPressurePlateBlock(ActivationRule rule, Settings settings) {
		super(rule, settings);
	}
}
