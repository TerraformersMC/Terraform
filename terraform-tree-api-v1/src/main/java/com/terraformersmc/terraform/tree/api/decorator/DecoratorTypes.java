package com.terraformersmc.terraform.tree.api.decorator;

import com.mojang.serialization.MapCodec;
import com.terraformersmc.terraform.tree.mixin.InvokerTreeDecoratorType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

@SuppressWarnings("unused")
public final class DecoratorTypes {
	@SuppressWarnings("UnnecessaryReturnStatement")
	private DecoratorTypes() {
		return;
	}

	public static <D extends TreeDecorator> TreeDecoratorType<D> registerTreeDecorator(String id, MapCodec<D> codec) {
		return InvokerTreeDecoratorType.callRegister(id, codec);
	}

	public static <D extends TreeDecorator> TreeDecoratorType<D> registerTreeDecorator(Identifier id, MapCodec<D> codec) {
		return registerTreeDecorator(id.toString(), codec);
	}
}
