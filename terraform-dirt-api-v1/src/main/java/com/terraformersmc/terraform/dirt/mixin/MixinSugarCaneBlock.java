package com.terraformersmc.terraform.dirt.mixin;

import com.terraformersmc.terraform.dirt.TerraformDirtBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SugarCaneBlock.class)
public class MixinSugarCaneBlock {
	@Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
	private void canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		BlockPos downPos = pos.down();

		if (world.getBlockState(downPos).isIn(TerraformDirtBlockTags.SOIL)) {

			for(Direction direction: Direction.Type.HORIZONTAL) {
				BlockState candidateState = world.getBlockState(downPos.offset(direction));
				FluidState fluidState = world.getFluidState(downPos.offset(direction));

				if (fluidState.isIn(FluidTags.WATER) || candidateState.getBlock() == Blocks.FROSTED_ICE) {
					info.setReturnValue(true);
				}
			}
		}
	}
}
