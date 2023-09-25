package com.terraformersmc.terraform.dirt.mixin;

import com.terraformersmc.terraform.dirt.DirtBlocks;
import com.terraformersmc.terraform.dirt.TerraformDirtBlockTags;
import com.terraformersmc.terraform.dirt.TerraformDirtRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecorator;

@Mixin(AlterGroundTreeDecorator.class)
public class MixinAlterGroundTreeDecorator {
	// prepareGroundColumn
	@Inject(method = "setColumn(Lnet/minecraft/world/gen/treedecorator/TreeDecorator$Generator;Lnet/minecraft/util/math/BlockPos;)V",
			at = @At("HEAD"),
			cancellable = true
	)
	private void terraformDirt$allowCustomPodzolPlacement(TreeDecorator.Generator generator, BlockPos pos, CallbackInfo ci) {
		for (int i = 2; i >= -3; --i) {
			BlockPos posUp = pos.up(i);

			// Test if there's a custom soil block at this location
			if (generator.getWorld().testBlockState(posUp, state -> state.isIn(TerraformDirtBlockTags.SOIL))) {
				// Try to determine if the soil block is registered, and if so, replace it with custom podzol.
				// Fall back to vanilla podzol if the soil block is unregistered.
				Block podzol = TerraformDirtRegistry.getFromWorld(generator.getWorld(), posUp).map(DirtBlocks::getPodzol).orElse(Blocks.PODZOL);

				generator.replace(posUp, podzol.getDefaultState());
				ci.cancel();
				return;
			}

			if (!generator.getWorld().testBlockState(posUp, BlockState::isAir) && i < 0) {
				break;
			}
		}
	}
}
