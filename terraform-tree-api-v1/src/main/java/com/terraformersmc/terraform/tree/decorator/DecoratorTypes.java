package com.terraformersmc.terraform.tree.decorator;

import com.mojang.serialization.MapCodec;
import com.terraformersmc.terraform.tree.mixin.InvokerTreeDecoratorType;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public final class DecoratorTypes {
	private DecoratorTypes() {
		return;
	}

	// Deprecated annotation is just a warning for non-internal references
	@SuppressWarnings("deprecation")
	public static <D extends TreeDecorator> TreeDecoratorType<D> registerTreeDecorator(String id, MapCodec<D> codec) {
		return InvokerTreeDecoratorType.callRegister(id, codec);
	}

	public static <D extends TreeDecorator> TreeDecoratorType<D> registerTreeDecorator(Identifier id, MapCodec<D> codec) {
		return registerTreeDecorator(id.toString(), codec);
	}
}
