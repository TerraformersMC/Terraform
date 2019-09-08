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
 * A custom farmland block for new farmland. Mixins are required to make hoes plant these blocks and to allow seeds to be planted.
 * */
public class TerraformFarmlandBlock extends Block {
	public static final IntProperty MOISTURE;
	protected static final VoxelShape SHAPE;
	private static Block trampled; //sets the block to revert to when trampled

	public TerraformFarmlandBlock(Settings block$Settings_1, Block trampled) {
		super(block$Settings_1);
		this.setDefaultState((BlockState)((BlockState)this.stateFactory.getDefaultState()).with(MOISTURE, 0));
		this.trampled = trampled;
	}

	public BlockState getStateForNeighborUpdate(BlockState blockState_1, Direction direction_1, BlockState blockState_2, IWorld iWorld_1, BlockPos blockPos_1, BlockPos blockPos_2) {
		if (direction_1 == Direction.UP && !blockState_1.canPlaceAt(iWorld_1, blockPos_1)) {
			iWorld_1.getBlockTickScheduler().schedule(blockPos_1, this, 1);
		}

		return super.getStateForNeighborUpdate(blockState_1, direction_1, blockState_2, iWorld_1, blockPos_1, blockPos_2);
	}

	public boolean canPlaceAt(BlockState blockState_1, ViewableWorld viewableWorld_1, BlockPos blockPos_1) {
		BlockState blockState_2 = viewableWorld_1.getBlockState(blockPos_1.up());
		return !blockState_2.getMaterial().isSolid() || blockState_2.getBlock() instanceof FenceGateBlock;
	}

	public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
		return !this.getDefaultState().canPlaceAt(itemPlacementContext_1.getWorld(), itemPlacementContext_1.getBlockPos()) ? trampled.getDefaultState() : super.getPlacementState(itemPlacementContext_1);
	}

	public boolean hasSidedTransparency(BlockState blockState_1) {
		return true;
	}

	public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext entityContext_1) {
		return SHAPE;
	}

	public void onScheduledTick(BlockState blockState_1, World world_1, BlockPos blockPos_1, Random random_1) {
		if (!blockState_1.canPlaceAt(world_1, blockPos_1)) {
			setToDirt(blockState_1, world_1, blockPos_1);
		} else {
			int int_1 = (Integer)blockState_1.get(MOISTURE);
			if (!isWaterNearby(world_1, blockPos_1) && !world_1.hasRain(blockPos_1.up())) {
				if (int_1 > 0) {
					world_1.setBlockState(blockPos_1, (BlockState)blockState_1.with(MOISTURE, int_1 - 1), 2);
				} else if (!hasCrop(world_1, blockPos_1)) {
					setToDirt(blockState_1, world_1, blockPos_1);
				}
			} else if (int_1 < 7) {
				world_1.setBlockState(blockPos_1, (BlockState)blockState_1.with(MOISTURE, 7), 2);
			}

		}
	}

	public void onLandedUpon(World world_1, BlockPos blockPos_1, Entity entity_1, float float_1) {
		if (!world_1.isClient && world_1.random.nextFloat() < float_1 - 0.5F && entity_1 instanceof LivingEntity && (entity_1 instanceof PlayerEntity || world_1.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) && entity_1.getWidth() * entity_1.getWidth() * entity_1.getHeight() > 0.512F) {
			setToDirt(world_1.getBlockState(blockPos_1), world_1, blockPos_1);
		}

		super.onLandedUpon(world_1, blockPos_1, entity_1, float_1);
	}

	public static void setToDirt(BlockState blockState_1, World world_1, BlockPos blockPos_1) {
		world_1.setBlockState(blockPos_1, pushEntitiesUpBeforeBlockChange(blockState_1, trampled.getDefaultState(), world_1, blockPos_1));
	}

	private static boolean hasCrop(BlockView blockView_1, BlockPos blockPos_1) {
		Block block_1 = blockView_1.getBlockState(blockPos_1.up()).getBlock();
		return block_1 instanceof CropBlock || block_1 instanceof StemBlock || block_1 instanceof AttachedStemBlock;
	}

	private static boolean isWaterNearby(ViewableWorld viewableWorld_1, BlockPos blockPos_1) {
		Iterator var2 = BlockPos.iterate(blockPos_1.add(-4, 0, -4), blockPos_1.add(4, 1, 4)).iterator();

		BlockPos blockPos_2;
		do {
			if (!var2.hasNext()) {
				return false;
			}

			blockPos_2 = (BlockPos)var2.next();
		} while(!viewableWorld_1.getFluidState(blockPos_2).matches(FluidTags.WATER));

		return true;
	}

	protected void appendProperties(Builder<Block, BlockState> stateFactory$Builder_1) {
		stateFactory$Builder_1.add(new Property[]{MOISTURE});
	}

	public boolean canPlaceAtSide(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, BlockPlacementEnvironment blockPlacementEnvironment_1) {
		return false;
	}

	static {
		MOISTURE = Properties.MOISTURE;
		SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);
	}
}
