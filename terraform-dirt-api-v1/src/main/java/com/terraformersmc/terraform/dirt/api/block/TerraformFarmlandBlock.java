package com.terraformersmc.terraform.dirt.api.block;

import com.terraformersmc.terraform.dirt.mixin.MixinFarmlandBlock;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * A custom farm block for new farmland.
 * Mixins are required to make hoes create these blocks and to allow seeds to be planted.
 * @see MixinFarmlandBlock
 */
@SuppressWarnings("unused")
public class TerraformFarmlandBlock extends FarmlandBlock {
	public TerraformFarmlandBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}
}
