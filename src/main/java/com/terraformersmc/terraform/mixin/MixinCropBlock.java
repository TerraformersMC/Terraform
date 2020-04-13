package com.terraformersmc.terraform.mixin;

import net.minecraft.block.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CropBlock.class)
public class MixinCropBlock {
	@Redirect(method = "getAvailableMoisture", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"))
	private static Block onGetAvailableMoisture(BlockState state) {
		Block block = state.getBlock();
		return block instanceof FarmlandBlock ? Blocks.FARMLAND : block;
	}
}
