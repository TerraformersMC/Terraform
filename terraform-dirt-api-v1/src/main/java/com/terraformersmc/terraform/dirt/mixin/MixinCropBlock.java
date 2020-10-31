package com.terraformersmc.terraform.dirt.mixin;

import net.minecraft.block.*;

import com.terraformersmc.terraform.dirt.TerraformDirtBlockTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CropBlock.class)
public class MixinCropBlock {
	@Redirect(method = "getAvailableMoisture(Lnet/minecraft/block/Block;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)F", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"))
	private static Block onGetAvailableMoisture(BlockState state) {
		Block block = state.getBlock();
		return block instanceof FarmlandBlock && block.isIn(TerraformDirtBlockTags.FARMLAND) ? Blocks.FARMLAND : block;
	}
}
