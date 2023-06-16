package com.terraformersmc.terraform.sign.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.terraformersmc.terraform.sign.BlockSettingsLock;

import net.minecraft.block.AbstractBlock;

@Mixin(AbstractBlock.Settings.class)
public class MixinAbstractBlockSettings implements BlockSettingsLock {
	@Unique
	private boolean terraform$locked = false;

	@Inject(method = "sounds", at = @At("HEAD"), cancellable = true)
	private void terraform$preventSoundsOverride(CallbackInfoReturnable<AbstractBlock.Settings> ci) {
		if (this.terraform$locked) {
			ci.setReturnValue((AbstractBlock.Settings) (Object) this);
			this.terraform$locked = false;
		}
	}

	@Override
	public void lock() {
		this.terraform$locked = true;
	}
}
