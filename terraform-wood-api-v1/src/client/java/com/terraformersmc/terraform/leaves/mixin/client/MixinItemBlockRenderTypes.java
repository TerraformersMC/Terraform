package com.terraformersmc.terraform.leaves.mixin.client;

import com.terraformersmc.terraform.leaves.api.block.ExtendedLeavesBlock;
import com.terraformersmc.terraform.wood.api.block.SmallLogBlock;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemBlockRenderTypes.class)
public class MixinItemBlockRenderTypes {
	@Inject(method = "getChunkRenderType", at = @At("HEAD"), cancellable = true)
	private static void terraformWood$onGetChunkRenderType(BlockState state, CallbackInfoReturnable<ChunkSectionLayer> cir) {
		Block block = state.getBlock();
		if (block instanceof ExtendedLeavesBlock || block instanceof SmallLogBlock && state.getValue(SmallLogBlock.HAS_LEAVES)) {
			cir.setReturnValue(ChunkSectionLayer.CUTOUT);
		}
	}
}
