package net.sssubtlety.automated_crafting;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.sssubtlety.automated_crafting.client.AutoCrafterScreen;

public class ClientInit implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MenuScreens.register(Registrar.MENU_TYPE, AutoCrafterScreen::new);

		final ModContainer modContainer = FabricLoader.getInstance().getModContainer(AutomatedCrafting.NAMESPACE).orElseThrow();
		ResourceLoader.registerBuiltinPack(AutomatedCrafting.id("powered_icon"), modContainer, PackActivationType.NORMAL);
		ResourceLoader.registerBuiltinPack(AutomatedCrafting.id("powered_block"), modContainer, PackActivationType.NORMAL);
	}
}
