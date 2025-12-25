package com.terraformersmc.terraform.dirt.api.block;

import com.terraformersmc.terraform.dirt.impl.mixin.MixinFarmBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;

/**
 * A custom farmland block for new farmland. Mixins are required to make hoes create these blocks and to allow seeds to be planted.
 * @see MixinFarmBlock
 */
public class TerraformFarmBlock extends FarmBlock {
	/**
	 * @deprecated the "trampled" block is no longer controlled by TerraformFarmBlock, use the other constructor.
	 */
	@Deprecated
	public TerraformFarmBlock(Properties settings, Block trampled) {
		super(settings);
	}

	public TerraformFarmBlock(Properties settings) {
		super(settings);
	}
}
