package io.github.terraformersmc.terraform.mixin;

import com.google.common.collect.ImmutableMap;
import io.github.terraformersmc.terraform.util.StrippedBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Mixin(AxeItem.class)
public class MixinAxeItem {
	@Shadow
	@Final
	@Mutable
	protected static Map<Block, Block> STRIPPED_BLOCKS;

	@Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
	public void onUsed(ItemUsageContext context, CallbackInfoReturnable<ActionResult> returnable) {
		if(StrippedBlocks.isStale()) {
			ImmutableMap.Builder<Block, Block> builder = new ImmutableMap.Builder<>();

			builder.putAll(STRIPPED_BLOCKS);
			StrippedBlocks.inject(builder);

			STRIPPED_BLOCKS = builder.build();
		}

		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);

		Optional<Function<BlockState, BlockState>> special = StrippedBlocks.getSpecialHandler(state.getBlock());

		special.ifPresent(handler -> {
			PlayerEntity player = context.getPlayer();
			world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);

			if (!world.isClient) {
				world.setBlockState(pos, handler.apply(state), 11);
				if (player != null) {
					context.getStack().damage(1, player, (targetPlayer) -> {
						targetPlayer.sendToolBreakStatus(context.getHand());
					});
				}
			}

			returnable.setReturnValue(ActionResult.SUCCESS);
		});
	}
}
