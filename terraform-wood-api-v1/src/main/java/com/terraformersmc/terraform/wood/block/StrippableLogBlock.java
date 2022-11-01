package com.terraformersmc.terraform.wood.block;

import java.util.function.Supplier;

import net.minecraft.block.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class StrippableLogBlock extends PillarBlock {
	private final Supplier<Block> stripped;

	public StrippableLogBlock(Supplier<Block> stripped, Settings settings) {
		super(settings);

		this.stripped = stripped;
	}

	@Deprecated
	public StrippableLogBlock(Supplier<Block> stripped, MapColor color, Settings settings) {
		this(stripped, settings);
	}

	public StrippableLogBlock of(Supplier<Block> stripped, MapColor color, Block.Settings settings) {
		return new StrippableLogBlock(stripped, settings.mapColor(color));
	}

	public StrippableLogBlock of(Supplier<Block> stripped, MapColor top, MapColor side) {
		return new StrippableLogBlock(stripped,
			Block.Settings.of(
				Material.WOOD,
				(state) -> Direction.Axis.Y.equals(state.get(PillarBlock.AXIS)) ? top : side
			).strength(2.0F).sounds(BlockSoundGroup.WOOD)
		);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack heldStack = player.getEquippedStack(hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);

		if (heldStack.isEmpty()) {
			return ActionResult.FAIL;
		}

		Item held = heldStack.getItem();
		if (!(held instanceof MiningToolItem tool)) {
			return ActionResult.FAIL;
		}

		if (stripped != null && (tool.getMiningSpeedMultiplier(heldStack, state) > 1.0F)) {
			world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);

			if (!world.isClient) {
				BlockState target = stripped.get().getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS));

				world.setBlockState(pos, target);

				heldStack.damage(1, player, consumedPlayer -> consumedPlayer.sendToolBreakStatus(hand));
			}

			return ActionResult.SUCCESS;
		}

		return ActionResult.FAIL;
	}
}
