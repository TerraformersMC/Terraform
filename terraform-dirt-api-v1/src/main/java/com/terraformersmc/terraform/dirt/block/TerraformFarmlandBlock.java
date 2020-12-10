package com.terraformersmc.terraform.dirt.block;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Iterator;
import java.util.Random;

/**
 * A custom farmland block for new farmland. Mixins are required to make hoes create these blocks and to allow seeds to be planted.
 * @see com.terraformersmc.terraform.dirt.mixin.MixinFarmlandBlock
 */
public class TerraformFarmlandBlock extends FarmlandBlock {
	/**
	 * @deprecated the "trampled" block is no longer controlled by TerraformFarmlandBlock, use the other constructor.
	 */
	@Deprecated
	public TerraformFarmlandBlock(Settings settings, Block trampled) {
		super(settings);
	}

	public TerraformFarmlandBlock(Settings settings) {
		super(settings);
	}
}
