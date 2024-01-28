package com.terraformersmc.terraform.biomeremapper.impl.mixin;

import com.terraformersmc.terraform.biomeremapper.impl.BiomeRemapper;
import com.terraformersmc.terraform.biomeremapper.impl.fix.BiomeIdFixData;
import net.fabricmc.fabric.impl.registry.sync.RegistryMapSerializer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtSizeTracker;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Mixin(LevelStorage.Session.class)
public class MixinLevelStorageSession {
	@Shadow
	@Final
	LevelStorage.LevelSave directory;

	@Unique
	@SuppressWarnings("UnstableApiUsage")
	private boolean terraformBiomeRemapper$readIdMapFile(File file) throws IOException {
		BiomeRemapper.LOGGER.debug("Reading registry data from " + file.toString());

		if (file.exists()) {
			FileInputStream fileInputStream = new FileInputStream(file);
			NbtCompound nbt = NbtIo.readCompressed(fileInputStream, NbtSizeTracker.ofUnlimitedBytes());
			fileInputStream.close();

			if (nbt != null) {
				BiomeIdFixData.applyFabricDynamicRegistryMap(RegistryMapSerializer.fromNbt(nbt));
				return true;
			}
		}

		return false;
	}

	@Inject(method = "readLevelProperties(Z)Lcom/mojang/serialization/Dynamic;", at = @At("HEAD"))
	public void terraformBiomeRemapper$readWorldProperties(CallbackInfoReturnable<SaveProperties> callbackInfo) {
		try {
			if (terraformBiomeRemapper$readIdMapFile(new File(new File(directory.path().toFile(), "data"), "fabricDynamicRegistry.dat"))) {
				BiomeRemapper.LOGGER.info("[Registry Sync Fix] Loaded registry data");
			}
		} catch (FileNotFoundException e) {
			// Pass
		} catch (IOException e) {
			BiomeRemapper.LOGGER.warn("[Registry Sync Fix] Reading registry file failed!", e);
		}
	}
}
