package net.sssubtlety.automated_crafting;

import net.fabricmc.api.ModInitializer;

import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.END_DATA_PACK_RELOAD;

public class Init implements ModInitializer {
	@Override
	public void onInitialize() {
		FeatureControl.init();
		Registrar.init();

		END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> AutoCrafterBlockEntity.validator.update());
	}
}
