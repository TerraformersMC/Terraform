package com.terraformersmc.terraform.sign;

import java.util.Collection;
import java.util.List;

import net.minecraft.client.util.SpriteIdentifier;

/**
 * @deprecated SpriteIdentifierRegistry is no longer used
 *
 * <p>It is no longer necessary to separately register the sign sprite identifiers.
 * This class is deprecated and will be removed.
 * Calls to {@link #addIdentifier(SpriteIdentifier)} are now no-ops.
 */
@Deprecated(forRemoval = true, since = "10.0.0")
public class SpriteIdentifierRegistry {
	public static final SpriteIdentifierRegistry INSTANCE = new SpriteIdentifierRegistry();

	/**
	 * @deprecated Ignores the provided sprite identifier
	 *
	 * @param sprite Sprite identifier which no longer needs to be registered
	 */
	@Deprecated(forRemoval = true, since = "10.0.0")
	public void addIdentifier(SpriteIdentifier sprite) {
	}

	/**
	 * @deprecated Returns an empty list
	 *
	 * @return Collection of registered sign sprite identifiers
	 */
	@Deprecated(forRemoval = true, since = "10.0.0")
	public Collection<SpriteIdentifier> getIdentifiers() {
		return List.of();
	}
}
