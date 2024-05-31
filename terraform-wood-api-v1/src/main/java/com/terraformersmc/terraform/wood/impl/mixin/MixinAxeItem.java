package com.terraformersmc.terraform.wood.impl.mixin;

import com.terraformersmc.terraform.wood.api.block.BareSmallLogBlock;
import com.terraformersmc.terraform.wood.api.block.QuarterLogBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AxeItem.class)
public class MixinAxeItem {
	@Inject(method = "getStrippedState", at = @At("TAIL"), cancellable = true)
	private void terraform$getStrippedState(BlockState oldState, CallbackInfoReturnable<Optional<BlockState>> cir) {
		cir.getReturnValue().ifPresent(newState -> {
			Block newBlock = newState.getBlock();
			// SmallLogBlock extends BareSmallLogBlock
			if (newBlock instanceof BareSmallLogBlock || newBlock instanceof QuarterLogBlock) {
				cir.setReturnValue(Optional.of(newBlock.getStateWithProperties(oldState)));
			}
		});
	}
}
