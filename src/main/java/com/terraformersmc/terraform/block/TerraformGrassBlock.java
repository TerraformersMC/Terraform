package com.terraformersmc.terraform.block;

import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

/**
 * A custom grass block that allows one to define their own soil types, used for things like basalt grass.
 */
public class TerraformGrassBlock extends GrassBlock {
	private Block dirt;
	private Supplier<Block> path;

	public TerraformGrassBlock(Block dirt, Supplier<Block> path, Block.Settings settings) {
		super(settings);

		this.dirt = dirt;
		this.path = path;
	}

	private static boolean canSurvive(BlockState state, WorldView world, BlockPos pos) {
		BlockPos above = pos.up();
		BlockState aboveState = world.getBlockState(above);

		if (aboveState.getBlock() == Blocks.SNOW && aboveState.get(SnowBlock.LAYERS) == 1) {
			return true;
		} else {
			int lightingAt = ChunkLightProvider.getRealisticOpacity(world, state, pos, aboveState, above, Direction.UP, aboveState.getOpacity(world, above));
			return lightingAt < world.getMaxLightLevel();
		}
	}

	private static boolean canSpread(BlockState state, WorldView world, BlockPos pos) {
		BlockPos above = pos.up();
		return canSurvive(state, world, pos) && !world.getFluidState(above).matches(FluidTags.WATER);
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!world.isClient) {
			if (!canSurvive(state, world, pos)) {
				world.setBlockState(pos, dirt.getDefaultState());
			} else if (world.getLightLevel(LightType.SKY, pos.up()) >= 4) {
				if (world.getLightLevel(LightType.SKY, pos.up()) >= 9) {
					BlockState defaultState = this.getDefaultState();

					for (int int_1 = 0; int_1 < 4; ++int_1) {
						BlockPos blockPos_2 = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);

						if (world.getBlockState(blockPos_2).getBlock() == dirt && canSpread(defaultState, world, blockPos_2)) {
							world.setBlockState(blockPos_2, defaultState.with(SNOWY, world.getBlockState(blockPos_2.up()).getBlock() == Blocks.SNOW));
						}
					}
				}

			}
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack heldStack = player.getEquippedStack(hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);

		if(heldStack.isEmpty()) {
			return ActionResult.FAIL;
		}

		Item held = heldStack.getItem();
		if(!(held instanceof MiningToolItem)) {
			return ActionResult.FAIL;
		}

		MiningToolItem tool = (MiningToolItem) held;

		if(hit.getSide() == Direction.DOWN || !world.getBlockState(pos.up()).isAir()) {
			return ActionResult.FAIL;
		}

		if(path != null && (tool.isEffectiveOn(state) || tool.getMiningSpeedMultiplier(heldStack, state) > 1.0F || tool instanceof ShovelItem)) {
			world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

			if(!world.isClient) {
				world.setBlockState(pos, path.get().getDefaultState());

				heldStack.damage(1, player, consumedPlayer -> consumedPlayer.sendToolBreakStatus(hand));
			}

			return ActionResult.SUCCESS;
		}

		return ActionResult.FAIL;
	}
}
