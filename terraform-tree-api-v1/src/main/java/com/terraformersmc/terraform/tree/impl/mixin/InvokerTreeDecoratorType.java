package com.terraformersmc.terraform.tree.impl.mixin;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TreeDecoratorType.class)
public interface InvokerTreeDecoratorType {
	@Invoker
	@Deprecated
	static <D extends TreeDecorator> TreeDecoratorType<D> callRegister(String id, MapCodec<D> codec) {
		throw new UnsupportedOperationException();
	}
}
