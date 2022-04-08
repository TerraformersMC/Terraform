package com.terraformersmc.terraform.tree.block;

import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;

/**
 * A simple wrapper around the parent SaplingBlock class allowing access to the constructor.
 * @deprecated Use the access widener provided by the {@code fabric-transitive-access-wideners-v1} module
 */
@Deprecated(forRemoval = true)
public class TerraformSaplingBlock extends SaplingBlock {

	public TerraformSaplingBlock(SaplingGenerator generator, Settings settings) {
		super(generator, settings);
	}

	public TerraformSaplingBlock(SaplingGenerator generator) {
		this(generator, Settings.copy(Blocks.OAK_SAPLING));
	}

}
