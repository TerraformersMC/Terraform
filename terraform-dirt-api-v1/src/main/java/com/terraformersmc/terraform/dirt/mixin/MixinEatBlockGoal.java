package com.terraformersmc.terraform.dirt.mixin;

import com.terraformersmc.terraform.dirt.api.DirtBlocks;
import com.terraformersmc.terraform.dirt.api.TerraformDirtBlockTags;
import com.terraformersmc.terraform.dirt.impl.registry.TerraformDirtRegistryImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EatBlockGoal.class)
public class MixinEatBlockGoal {
	@Shadow
	@Final
	private Mob mob;

	@Shadow
	@Final
	private Level level;

	@Inject(method = "canUse",
			at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;GRASS_BLOCK:Lnet/minecraft/world/level/block/Block;"),
			cancellable = true,
			locals = LocalCapture.NO_CAPTURE
	)
	private void terraformDirt$startOnCustomGrass(CallbackInfoReturnable<Boolean> cir) {
		BlockPos pos = this.mob.blockPosition();

		if (this.level.getBlockState(pos.below()).is(TerraformDirtBlockTags.GRASS_BLOCKS)) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "tick",
			at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;GRASS_BLOCK:Lnet/minecraft/world/level/block/Block;"),
			locals = LocalCapture.NO_CAPTURE
	)
	private void terraformDirt$finishEatingOnCustomGrass(CallbackInfo ci) {
		BlockPos downPos = this.mob.blockPosition().below();
		BlockState down = this.level.getBlockState(downPos);

		if (down.is(TerraformDirtBlockTags.GRASS_BLOCKS)) {
			if (((ServerLevel) this.level).getGameRules().get(GameRules.MOB_GRIEFING)) {
				this.level.levelEvent(2001, downPos, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));

				Block replacement = TerraformDirtRegistryImpl.getByGrassBlock(down.getBlock())
					.map(DirtBlocks::dirtBlock).orElse(Blocks.DIRT);

				this.level.setBlock(downPos, replacement.defaultBlockState(), 2);
			}

			this.mob.ate();
		}
	}
}
