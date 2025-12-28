package com.terraformersmc.terraform.tree.mixin;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TreeDecoratorType.class)
public interface InvokerTreeDecoratorType {
	@Invoker
	static <D extends TreeDecorator> TreeDecoratorType<D> callRegister(String id, MapCodec<D> codec) {
		throw new UnsupportedOperationException();
	}
}
