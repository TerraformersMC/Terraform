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

	private static final String GRASS_BLOCK = "Lnet/minecraft/block/Blocks;GRASS_BLOCK:Lnet/minecraft/block/Block;";

	@Inject(method = "canStart", at = @At(value = "FIELD", target = GRASS_BLOCK), cancellable = true)
	private void terraform$startOnCustomGrass(CallbackInfoReturnable<Boolean> callbackInfo) {
		BlockPos pos = this.mob.getBlockPos();

		if (this.world.getBlockState(pos.down()).isIn(TerraformDirtBlockTags.GRASS_BLOCKS)) {
			callbackInfo.setReturnValue(true);
		}
	}

	@Inject(method = "tick", at = @At(value = "FIELD", target = GRASS_BLOCK))
	private void terraform$finishEatingOnCustomGrass(CallbackInfo info) {
		BlockPos downPos = this.mob.getBlockPos().down();
		BlockState down = this.world.getBlockState(downPos);

		if (down.isIn(TerraformDirtBlockTags.GRASS_BLOCKS)) {
			if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
				this.world.syncWorldEvent(2001, downPos, Block.getRawIdFromState(Blocks.GRASS_BLOCK.getDefaultState()));

				Block replacement = TerraformDirtRegistry.getByGrassBlock(down.getBlock()).map(DirtBlocks::getDirt).orElse(Blocks.DIRT);

				this.world.setBlockState(downPos, replacement.getDefaultState(), 2);
			}

			this.mob.onEatingGrass();
		}
	}
}
