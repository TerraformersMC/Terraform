package com.terraformersmc.terraform.dirt.mixin;

import com.terraformersmc.terraform.dirt.TerraformDirtRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(FarmlandBlock.class)
public class MixinFarmlandBlock {
	@Inject(method = "getPlacementState(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/block/BlockState;",
	        at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;DIRT:Lnet/minecraft/block/Block;"),
	        cancellable = true)
	private void terraform$setCustomDirtInBlockPlacement(ItemPlacementContext context, CallbackInfoReturnable<BlockState> cir) {
		// If this is custom farmland, make sure that we don't set back to vanilla dirt
		TerraformDirtRegistry.getByFarmland((Block) (Object) this).ifPresent(blocks -> {
			cir.setReturnValue(blocks.getDirt().getDefaultState());
		});
	}

	@Inject(method = "setToDirt(Lnet/minecraft/entity/Entity;Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
	        at = @At("HEAD"), cancellable = true)
	private static void terraform$setCustomDirt(Entity entity, BlockState state, World world, BlockPos pos, CallbackInfo ci) {
		// If this is custom farmland, make sure that we don't set back to vanilla dirt
		TerraformDirtRegistry.getByFarmland(state.getBlock()).ifPresent(blocks -> {
			world.setBlockState(pos, Block.pushEntitiesUpBeforeBlockChange(state, blocks.getDirt().getDefaultState(), world, pos));

			ci.cancel();
		});
	}
}
