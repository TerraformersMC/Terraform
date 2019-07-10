package io.github.terraformersmc.terraform.mixin;

import io.github.terraformersmc.terraform.util.TerraformSign;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityType.class)
public class MixinBlockEntityType {
    //    @ModifyArg(method = "create", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableSet;copyOf([Ljava/lang/Object;)Lcom/google/common/collect/ImmutableSet;"))
//    private static Object[] injectTerraformSigns(Object[] blocks) {
//        if (blocks != null && blocks.length > 0 && Arrays.asList(blocks).contains(Blocks.OAK_SIGN)) {
//            blocks = ArrayUtils.addAll(blocks, Signs.getSigns().keySet().toArray());
//        }
//        return blocks;
//    }
    @Inject(method = "supports", at = @At("HEAD"), cancellable = true)
    private void supports(Block block, CallbackInfoReturnable info) {
        //noinspection EqualsBetweenInconvertibleTypes
        if (BlockEntityType.SIGN.equals(this) && block instanceof TerraformSign) {
            //noinspection unchecked
            info.setReturnValue(true);
        }
    }
}
