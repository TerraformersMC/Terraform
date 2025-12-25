package com.terraformersmc.terraform.wood.impl.mixin;

import com.terraformersmc.terraform.wood.api.block.BareSmallLogBlock;
import com.terraformersmc.terraform.wood.api.block.QuarterLogBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(AxeItem.class)
public class MixinAxeItem {
	@Inject(method = "getStripped", at = @At("TAIL"), cancellable = true)
	private void terraform$getStrippedState(BlockState oldState, CallbackInfoReturnable<Optional<BlockState>> cir) {
		cir.getReturnValue().ifPresent(newState -> {
			Block newBlock = newState.getBlock();
			// SmallLogBlock extends BareSmallLogBlock
			if (newBlock instanceof BareSmallLogBlock || newBlock instanceof QuarterLogBlock) {
				cir.setReturnValue(Optional.of(newBlock.withPropertiesOf(oldState)));
			}
		});
	}
}
