package com.terraformersmc.terraform.dirt.mixin;

import com.terraformersmc.terraform.dirt.api.TerraformDirtBlockTags;
import com.terraformersmc.terraform.dirt.impl.registry.TerraformDirtRegistryImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AlterGroundDecorator.class)
public class MixinAlterGroundDecorator {
	// prepareGroundColumn
	@Inject(method = "placeBlockAt(Lnet/minecraft/world/level/levelgen/feature/treedecorators/TreeDecorator$Context;Lnet/minecraft/core/BlockPos;)V",
			at = @At("HEAD"),
			cancellable = true
	)
	private void terraformDirt$allowCustomPodzolPlacement(TreeDecorator.Context context, BlockPos pos, CallbackInfo ci) {
		for (int i = 2; i >= -3; --i) {
			BlockPos posUp = pos.above(i);

			// Test if there's a custom soil block at this location
			if (context.level().isStateAtPosition(posUp, state -> state.is(TerraformDirtBlockTags.SOIL))) {
				// Try to determine if the soil block is registered, and if so, replace it with custom podzol.
				// Fall back to vanilla podzol if the soil block is unregistered.
				Block podzol = TerraformDirtRegistryImpl.getFromLevel(context.level(), posUp)
					.map(dirtBlocks -> (Block) dirtBlocks.podzolBlock()).orElse(Blocks.PODZOL);

				context.setBlock(posUp, podzol.defaultBlockState());
				ci.cancel();
				return;
			}

			if (!context.level().isStateAtPosition(posUp, BlockState::isAir) && i < 0) {
				break;
			}
		}
	}
}
