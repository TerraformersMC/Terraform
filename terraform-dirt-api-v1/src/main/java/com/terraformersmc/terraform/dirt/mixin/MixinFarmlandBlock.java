package com.terraformersmc.terraform.dirt.mixin;

import com.terraformersmc.terraform.dirt.impl.registry.TerraformDirtRegistryImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FarmlandBlock.class)
public class MixinFarmlandBlock extends Block {
	public MixinFarmlandBlock(Properties properties) {
		super(properties);
	}

	@Inject(method = "getStateForPlacement",
	        at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;DIRT:Lnet/minecraft/world/level/block/Block;"),
	        cancellable = true,
			locals = LocalCapture.NO_CAPTURE
	)
	private void terraformDirt$setCustomDirtInBlockPlacement(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
		// If this is custom farmland, make sure that we don't set back to vanilla dirt
		TerraformDirtRegistryImpl.getByFarmBlock(this)
				.ifPresent(blocks -> cir.setReturnValue(blocks.dirtBlock().defaultBlockState()));
	}

	@Inject(method = "turnToDirt",
	        at = @At("HEAD"),
			cancellable = true,
			locals = LocalCapture.NO_CAPTURE
	)
	private static void terraformDirt$setCustomDirt(Entity entity, BlockState state, Level level, BlockPos pos, CallbackInfo ci) {
		// If this is custom dirt block, make sure that we don't set back to vanilla dirt.
		// Note: as of 1.20.2, vanilla uses FarmlandBlock.setToDirt() for all trample-able blocks;
		// we are not responsible for evaluating whether the block can be trampled, here.
		TerraformDirtRegistryImpl.getFromLevel(level, pos).ifPresent(blocks -> {
			BlockState dirtState = FarmlandBlock.pushEntitiesUp(state, blocks.dirtBlock().defaultBlockState(), level, pos);
			level.setBlockAndUpdate(pos, dirtState);
			level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, dirtState));

			ci.cancel();
		});
	}
}
