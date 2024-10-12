package com.terraformersmc.terraform.sign.impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.terraformersmc.terraform.sign.api.TerraformSign;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractSignEditScreen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SignEditScreen.class)
@Environment(EnvType.CLIENT)
public abstract class MixinSignEditScreen extends AbstractSignEditScreen {
	public MixinSignEditScreen(SignBlockEntity blockEntity, boolean front, boolean filtered) {
		super(blockEntity, front, filtered);
	}

	@WrapOperation(
			// DrawContext#draw callback within the renderSignBackground method
			method = "method_64048",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getSignTextureId(Lnet/minecraft/block/WoodType;)Lnet/minecraft/client/util/SpriteIdentifier;")
	)
	@SuppressWarnings("unused")
	private SpriteIdentifier terraformWood$editSignTextureId(WoodType type, Operation<SpriteIdentifier> original, DrawContext drawContext) {
		BlockState state = this.blockEntity.getCachedState();

		if (state.getBlock() instanceof TerraformSign signBlock) {
			return new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, signBlock.getTexture());
		}

		return original.call(type);
	}
}
