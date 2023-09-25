package com.terraformersmc.terraform.dirt.mixin;

import com.terraformersmc.terraform.dirt.TerraformDirtRegistry;
import net.minecraft.world.event.GameEvent;
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
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FarmlandBlock.class)
public class MixinFarmlandBlock extends Block {
	public MixinFarmlandBlock(Settings settings) {
		super(settings);
	}

	@Inject(method = "getPlacementState",
	        at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;DIRT:Lnet/minecraft/block/Block;"),
	        cancellable = true,
			locals = LocalCapture.NO_CAPTURE
	)
	private void terraformDirt$setCustomDirtInBlockPlacement(ItemPlacementContext context, CallbackInfoReturnable<BlockState> cir) {
		// If this is custom farmland, make sure that we don't set back to vanilla dirt
		TerraformDirtRegistry.getByFarmland(this).ifPresent(blocks -> {
			cir.setReturnValue(blocks.getDirt().getDefaultState());
		});
	}

	@Inject(method = "setToDirt",
	        at = @At("HEAD"),
			cancellable = true,
			locals = LocalCapture.NO_CAPTURE
	)
	private static void terraformDirt$setCustomDirt(Entity entity, BlockState state, World world, BlockPos pos, CallbackInfo ci) {
		// If this is custom dirt block, make sure that we don't set back to vanilla dirt.
		// Note: as of 1.20.2, vanilla uses FarmlandBlock.setToDirt() for all trample-able blocks;
		// we are not responsible for evaluating whether the block can be trampled, here.
		TerraformDirtRegistry.getFromWorld(world, pos).ifPresent(blocks -> {
			BlockState dirtState = FarmlandBlock.pushEntitiesUpBeforeBlockChange(state, blocks.getDirt().getDefaultState(), world, pos);
			world.setBlockState(pos, dirtState);
			world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(entity, dirtState));

			ci.cancel();
		});
	}
}
