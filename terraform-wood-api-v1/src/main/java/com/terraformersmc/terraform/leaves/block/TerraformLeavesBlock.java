package com.terraformersmc.terraform.leaves.block;

import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;

/**
 * A simple wrapper around the parent LeavesBlock class allowing access to the constructor.
 * @deprecated Use the access widener provided by the {@code fabric-transitive-access-wideners-v1} module
 */
@Deprecated(forRemoval = true)
public class TerraformLeavesBlock extends LeavesBlock {

	public TerraformLeavesBlock() {
		super(Settings.copy(Blocks.OAK_LEAVES));
	}

}
