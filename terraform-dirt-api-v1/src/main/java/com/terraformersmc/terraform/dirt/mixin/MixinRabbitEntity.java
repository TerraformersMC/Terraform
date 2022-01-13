package com.terraformersmc.terraform.dirt.mixin;

import com.terraformersmc.terraform.dirt.TerraformDirtBlockTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CarrotsBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

@Mixin(targets = "net.minecraft.entity.passive.RabbitEntity$EatCarrotCropGoal")
public class MixinRabbitEntity {

	@Shadow
	private boolean wantsCarrots;

	@Shadow
	private boolean hasTarget;

	@Inject(method = "isTargetPos(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z", at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;FARMLAND:Lnet/minecraft/block/Block;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private void onIsTargetBlock(WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> info, BlockState state) {
		Block block = state.getBlock();
		if (block instanceof FarmlandBlock && TerraformDirtBlockTags.FARMLAND.contains(block) && this.wantsCarrots && !this.hasTarget) {
			state = world.getBlockState(pos.up());
			block = state.getBlock();

			if (block instanceof CarrotsBlock && ((CarrotsBlock) block).isMature(state)) {
				this.hasTarget = true;
				info.setReturnValue(true);
			}
		}
	}
}
