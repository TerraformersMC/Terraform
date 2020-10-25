package com.terraformersmc.terraform.dirt.mixin;

import com.terraformersmc.terraform.dirt.DirtBlocks;
import com.terraformersmc.terraform.dirt.TerraformDirtBlockTags;
import com.terraformersmc.terraform.dirt.TerraformDirtRegistry;
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

@Mixin(EatGrassGoal.class)
public class MixinEatGrassGoal {
	@Shadow
	@Final
	private MobEntity mob;

	@Shadow
	@Final
	private World world;

	@Inject(method = "canStart", at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;GRASS_BLOCK:Lnet/minecraft/block/Block;"), cancellable = true)
	private void terraform$startOnCustomGrass(CallbackInfoReturnable<Boolean> callbackInfo) {
		BlockPos pos = this.mob.getPositionTarget();

		if (this.world.getBlockState(pos.down()).isIn(TerraformDirtBlockTags.GRASS_BLOCKS)) {
			callbackInfo.setReturnValue(true);
		}
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;down()Lnet/minecraft/util/math/BlockPos;"))
	private void terraform$finishEatingOnCustomGrass(CallbackInfo info) {
		BlockPos pos = this.mob.getBlockPos().down();
		BlockState down = this.world.getBlockState(pos.down());

		if (down.isIn(TerraformDirtBlockTags.GRASS_BLOCKS)) {
			if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
				this.world.syncWorldEvent(2001, pos, Block.getRawIdFromState(Blocks.GRASS_BLOCK.getDefaultState()));

				Block replacement = TerraformDirtRegistry.getByGrassBlock(down.getBlock()).map(DirtBlocks::getDirt).orElse(Blocks.DIRT);

				this.world.setBlockState(pos, replacement.getDefaultState(), 2);
			}

			this.mob.onEatingGrass();
		}
	}
}
