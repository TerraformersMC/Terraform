package com.terraformersmc.terraform.sign;

import net.minecraft.block.AbstractBlock;

/**
 * An <b>internal</b> interface implemented on {@link AbstractBlock.Settings} that is used to prevent sign blocks from overriding their block sound group to the default wood block sound group.
 */
public interface BlockSettingsLock {
	/**
	 * Locks the block sound group.
	 */
	public void lock();

	/**
	 * Locks the block sound group.
	 */
	public static AbstractBlock.Settings lock(AbstractBlock.Settings settings) {
		((BlockSettingsLock) settings).lock();
		return settings;
	}
}
