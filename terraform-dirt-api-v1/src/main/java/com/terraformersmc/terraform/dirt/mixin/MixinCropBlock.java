package com.terraformersmc.terraform.dirt.mixin;

import net.minecraft.block.*;

import com.terraformersmc.terraform.dirt.TerraformDirtBlockTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CropBlock.class)
public class MixinCropBlock {
	@Redirect(method = "getAvailableMoisture(Lnet/minecraft/block/Block;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)F", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/AbstractBlock$AbstractBlockState;isOf(Lnet/minecraft/block/Block;)Z"))
	private static boolean onGetAvailableMoisture(AbstractBlock.AbstractBlockState state, Block block) {
		return state.isOf(block) || (block == Blocks.FARMLAND && TerraformDirtBlockTags.FARMLAND.contains(state.getBlock()));
	}
}
