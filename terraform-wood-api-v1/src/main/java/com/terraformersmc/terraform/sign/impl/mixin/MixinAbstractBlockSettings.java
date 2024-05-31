package com.terraformersmc.terraform.sign.impl.mixin;

import com.terraformersmc.terraform.sign.impl.BlockSettingsLock;
import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.Settings.class)
public class MixinAbstractBlockSettings implements BlockSettingsLock {
	@Unique
	private boolean terraform$locked = false;

	@Inject(method = "sounds", at = @At("HEAD"), cancellable = true)
	private void terraformWood$preventSoundsOverride(CallbackInfoReturnable<AbstractBlock.Settings> cir) {
		if (this.terraform$locked) {
			//noinspection ConstantConditions
			cir.setReturnValue((AbstractBlock.Settings) (Object) this);
			this.terraform$locked = false;
		}
	}

	@Override
	public void lock() {
		this.terraform$locked = true;
	}
}
