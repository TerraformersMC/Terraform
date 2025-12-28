package com.terraformersmc.terraform.dirt.api.block;

import com.terraformersmc.terraform.dirt.mixin.MixinFarmBlock;
import net.minecraft.world.level.block.FarmBlock;

/**
 * A custom farm block for new farmland.
 * Mixins are required to make hoes create these blocks and to allow seeds to be planted.
 * @see MixinFarmBlock
 */
@SuppressWarnings("unused")
public class TerraformFarmBlock extends FarmBlock {
	public TerraformFarmBlock(Properties properties) {
		super(properties);
	}
}
