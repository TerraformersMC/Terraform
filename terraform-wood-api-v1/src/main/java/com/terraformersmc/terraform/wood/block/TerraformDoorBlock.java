package com.terraformersmc.terraform.wood.block;

import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;

/**
 * A simple wrapper around the parent DoorBlock class allowing access to the constructor.
 * @deprecated Use the access widener provided by the {@code fabric-transitive-access-wideners-v1} module
 */
@Deprecated(forRemoval = true)
public class TerraformDoorBlock extends DoorBlock {
	public TerraformDoorBlock(Block.Settings settings) {
		super(settings);
	}
}
