package com.terraformersmc.terraform.dirt.impl.mixin;

import com.terraformersmc.terraform.dirt.impl.registry.TerraformDirtRegistryImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FarmBlock.class)
public class MixinFarmlandBlock extends Block {
	public MixinFarmlandBlock(Properties settings) {
		super(settings);
	}

	@Inject(method = "getStateForPlacement",
	        at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;DIRT:Lnet/minecraft/world/level/block/Block;"),
	        cancellable = true,
			locals = LocalCapture.NO_CAPTURE
	)
	private void terraformDirt$setCustomDirtInBlockPlacement(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
		// If this is custom farmland, make sure that we don't set back to vanilla dirt
		TerraformDirtRegistryImpl.getByFarmland(this)
				.ifPresent(blocks -> cir.setReturnValue(blocks.getDirt().defaultBlockState()));
	}

	@Inject(method = "turnToDirt",
	        at = @At("HEAD"),
			cancellable = true,
			locals = LocalCapture.NO_CAPTURE
	)
	private static void terraformDirt$setCustomDirt(Entity entity, BlockState state, Level world, BlockPos pos, CallbackInfo ci) {
		// If this is custom dirt block, make sure that we don't set back to vanilla dirt.
		// Note: as of 1.20.2, vanilla uses FarmlandBlock.setToDirt() for all trample-able blocks;
		// we are not responsible for evaluating whether the block can be trampled, here.
		TerraformDirtRegistryImpl.getFromWorld(world, pos).ifPresent(blocks -> {
			BlockState dirtState = FarmBlock.pushEntitiesUp(state, blocks.getDirt().defaultBlockState(), world, pos);
			world.setBlockAndUpdate(pos, dirtState);
			world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, dirtState));

			ci.cancel();
		});
	}
}
