package com.terraformersmc.terraform.wood.block;

import net.minecraft.block.Block;
import net.minecraft.block.StoneButtonBlock;

/**
 * A simple wrapper around the parent StoneButtonBlock class allowing access to the constructor.
 * @deprecated Use the access widener provided by the {@code fabric-transitive-access-wideners-v1} module
 */
@Deprecated(forRemoval = true)
public class TerraformStoneButtonBlock extends StoneButtonBlock {
	public TerraformStoneButtonBlock(Block.Settings settings) {
		super(settings);
	}
}
