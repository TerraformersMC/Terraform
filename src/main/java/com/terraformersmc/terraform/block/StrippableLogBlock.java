package com.terraformersmc.terraform.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class StrippableLogBlock extends LogBlock {
	private Supplier<Block> stripped;

	public StrippableLogBlock(Supplier<Block> stripped, MaterialColor top, Settings settings) {
		super(top, settings);

		this.stripped = stripped;
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

		if(stripped != null && (tool.isEffectiveOn(state) || tool.getMiningSpeed(heldStack, state) > 1.0F)) {
			world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);

			if(!world.isClient) {
				BlockState target = stripped.get().getDefaultState().with(LogBlock.AXIS, state.get(LogBlock.AXIS));

				world.setBlockState(pos, target);

				heldStack.damage(1, player, consumedPlayer -> consumedPlayer.sendToolBreakStatus(hand));
			}

			return ActionResult.SUCCESS;
		}

		return ActionResult.FAIL;
	}
}
