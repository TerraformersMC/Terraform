package com.terraformersmc.terraform.biomeremapper.impl.mixin;

import com.terraformersmc.terraform.biomeremapper.impl.BiomeRemapper;
import com.terraformersmc.terraform.biomeremapper.impl.fix.BiomeIdFixData;
import net.fabricmc.fabric.impl.registry.sync.RegistryMapSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
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

@Mixin(LevelStorageSource.LevelStorageAccess.class)
public class MixinLevelStorageAccess {
	@Shadow
	@Final
	LevelStorageSource.LevelDirectory levelDirectory;

	@Unique
	private boolean terraformBiomeRemapper$readIdMapFile(File file) throws IOException {
		BiomeRemapper.LOGGER.debug("Reading registry data from {}", file.toString());

		if (file.exists()) {
			FileInputStream fileInputStream = new FileInputStream(file);
			CompoundTag nbt = NbtIo.readCompressed(fileInputStream, NbtAccounter.unlimitedHeap());
			fileInputStream.close();

			if (nbt != null) {
				BiomeIdFixData.applyFabricDynamicRegistryMap(RegistryMapSerializer.fromNbt(nbt));

				return true;
			}
		}

		return false;
	}

	@Inject(method = "getDataTag(Z)Lcom/mojang/serialization/Dynamic;", at = @At("HEAD"))
	public void terraformBiomeRemapper$readWorldProperties(CallbackInfoReturnable<WorldData> callbackInfo) {
		try {
			if (terraformBiomeRemapper$readIdMapFile(new File(new File(levelDirectory.path().toFile(), "data"), "fabricDynamicRegistry.dat"))) {
				BiomeRemapper.LOGGER.info("[Registry Sync Fix] Loaded registry data");
			}
		} catch (FileNotFoundException e) {
			// Pass
		} catch (IOException e) {
			BiomeRemapper.LOGGER.warn("[Registry Sync Fix] Reading registry file failed!", e);
		}
	}
}
