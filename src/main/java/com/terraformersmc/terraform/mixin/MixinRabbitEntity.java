package com.terraformersmc.terraform.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CarrotsBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(targets = "net.minecraft.entity.passive.RabbitEntity$EatCarrotCropGoal")
public class MixinRabbitEntity {

	@Shadow
	private boolean wantsCarrots;

	@Shadow
	private boolean field_6861;

	@Inject(method = "isTargetPos", at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;FARMLAND:Lnet/minecraft/block/Block;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private void onIsTargetBlock(WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> info, Block block) {
		if (block instanceof FarmlandBlock && this.wantsCarrots && !this.field_6861) {
			pos = pos.up();
			BlockState state = world.getBlockState(pos);
			block = state.getBlock();
			if (block instanceof CarrotsBlock && ((CarrotsBlock) block).isMature(state)) {
				this.field_6861 = true;
				info.setReturnValue(true);
			}
		}
	}
}
