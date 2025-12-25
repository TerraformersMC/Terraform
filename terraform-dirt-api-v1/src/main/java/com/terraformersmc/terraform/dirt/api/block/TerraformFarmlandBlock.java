package com.terraformersmc.terraform.dirt.api.block;

import com.terraformersmc.terraform.dirt.impl.mixin.MixinFarmBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;

/**
 * A custom farmland block for new farmland. Mixins are required to make hoes create these blocks and to allow seeds to be planted.
 * @see MixinFarmBlock
 */
public class TerraformFarmlandBlock extends FarmBlock {
	/**
	 * @deprecated the "trampled" block is no longer controlled by TerraformFarmlandBlock, use the other constructor.
	 */
	@Deprecated
	public TerraformFarmlandBlock(Properties settings, Block trampled) {
		super(settings);
	}

	public TerraformFarmlandBlock(Properties settings) {
		super(settings);
	}
}
