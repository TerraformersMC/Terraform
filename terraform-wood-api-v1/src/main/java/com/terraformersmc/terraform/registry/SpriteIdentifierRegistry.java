package com.terraformersmc.terraform.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.util.SpriteIdentifier;

public class SpriteIdentifierRegistry {
	public static final SpriteIdentifierRegistry INSTANCE = new SpriteIdentifierRegistry();
	private final List<SpriteIdentifier> identifiers;

	private SpriteIdentifierRegistry() {
		identifiers = new ArrayList<>();
	}

	public void addIdentifier(SpriteIdentifier sprite) {
		this.identifiers.add(sprite);
	}

	public Collection<SpriteIdentifier> getIdentifiers() {
		return Collections.unmodifiableList(identifiers);
	}
}
