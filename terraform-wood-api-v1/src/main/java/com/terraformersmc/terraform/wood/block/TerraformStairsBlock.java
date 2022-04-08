package com.terraformersmc.terraform.wood.block;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;

/**
 * A simple wrapper around the parent StairsBlock class allowing access to the constructor.
 * @deprecated Use the access widener provided by the {@code fabric-transitive-access-wideners-v1} module
 */
@Deprecated(forRemoval = true)
public class TerraformStairsBlock extends StairsBlock {
	public TerraformStairsBlock(Block base, Settings settings) {
		super(base.getDefaultState(), settings);
	}
}
