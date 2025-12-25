package com.terraformersmc.terraform.tree.api.decorator;

import com.mojang.serialization.MapCodec;
import com.terraformersmc.terraform.tree.impl.mixin.InvokerTreeDecoratorType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

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
