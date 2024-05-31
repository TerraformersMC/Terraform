package com.terraformersmc.terraform.dirt.impl.mixin;

import com.terraformersmc.terraform.dirt.api.DirtBlocks;
import com.terraformersmc.terraform.dirt.api.TerraformDirtBlockTags;
import com.terraformersmc.terraform.dirt.impl.registry.TerraformDirtRegistryImpl;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EatGrassGoal.class)
public class MixinEatGrassGoal {
	@Shadow
	@Final
	private MobEntity mob;

	@Shadow
	@Final
	private World world;

	@Inject(method = "canStart",
			at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;GRASS_BLOCK:Lnet/minecraft/block/Block;"),
			cancellable = true,
			locals = LocalCapture.NO_CAPTURE
	)
	private void terraformDirt$startOnCustomGrass(CallbackInfoReturnable<Boolean> cir) {
		BlockPos pos = this.mob.getBlockPos();

		if (this.world.getBlockState(pos.down()).isIn(TerraformDirtBlockTags.GRASS_BLOCKS)) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "tick",
			at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;GRASS_BLOCK:Lnet/minecraft/block/Block;"),
			locals = LocalCapture.NO_CAPTURE
	)
	private void terraformDirt$finishEatingOnCustomGrass(CallbackInfo ci) {
		BlockPos downPos = this.mob.getBlockPos().down();
		BlockState down = this.world.getBlockState(downPos);

		if (down.isIn(TerraformDirtBlockTags.GRASS_BLOCKS)) {
			if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
				this.world.syncWorldEvent(2001, downPos, Block.getRawIdFromState(Blocks.GRASS_BLOCK.getDefaultState()));

				Block replacement = TerraformDirtRegistryImpl.getByGrassBlock(down.getBlock()).map(DirtBlocks::getDirt).orElse(Blocks.DIRT);

				this.world.setBlockState(downPos, replacement.getDefaultState(), 2);
			}

			this.mob.onEatingGrass();
		}
	}
}
