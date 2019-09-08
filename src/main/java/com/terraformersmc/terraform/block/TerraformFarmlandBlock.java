package com.terraformersmc.terraform.block;


import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.*;
import net.minecraft.block.Block.Settings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory.Builder;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorld;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;

/**
 * A custom farmland block for new farmland. Mixins are required to make hoes make these blocks and to allow seeds to be planted.
 * */
public class TerraformFarmlandBlock extends FarmlandBlock {
	private static Block trampled; //sets the block to revert to when trampled

	public TerraformFarmlandBlock(Settings settings, Block trampled) {
		super(settings);
		this.setDefaultState(this.stateFactory.getDefaultState().with(MOISTURE, 0));
		this.trampled = trampled;
	}

	public BlockState getPlacementState(ItemPlacementContext context) {
		return !this.getDefaultState().canPlaceAt(context.getWorld(), context.getBlockPos()) ? trampled.getDefaultState() : super.getPlacementState(context);
	}

	public void onScheduledTick(BlockState state, World world, BlockPos pos, Random rand) {
		if (!state.canPlaceAt(world, pos)) {
			setToDirt(state, world, pos);
		} else {
			int i = state.get(MOISTURE);
			if (!isWaterNearby(world, pos) && !world.hasRain(pos.up())) {
				if (i > 0) {
					world.setBlockState(pos, state.with(MOISTURE, i - 1), 2);
				} else if (!hasCrop(world, pos)) {
					setToDirt(state, world, pos);
				}
			} else if (i < 7) {
				world.setBlockState(pos, state.with(MOISTURE, 7), 2);
			}

		}
	}

	public static void setToDirt(BlockState state, World world, BlockPos pos) {
		world.setBlockState(pos, pushEntitiesUpBeforeBlockChange(state, trampled.getDefaultState(), world, pos));
	}

	private static boolean hasCrop(BlockView view, BlockPos pos) {
		Block block = view.getBlockState(pos.up()).getBlock();
		return block instanceof CropBlock || block instanceof StemBlock || block instanceof AttachedStemBlock;
	}

	private static boolean isWaterNearby(ViewableWorld world, BlockPos pos) {
		Iterator it = BlockPos.iterate(pos.add(-4, 0, -4), pos.add(4, 1, 4)).iterator();

		BlockPos blockPos_2;
		do {
			if (!it.hasNext()) {
				return false;
			}

			blockPos_2 = (BlockPos)it.next();
		} while(!world.getFluidState(blockPos_2).matches(FluidTags.WATER));

		return true;
	}
}
