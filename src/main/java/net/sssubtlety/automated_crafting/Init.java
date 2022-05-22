package net.sssubtlety.automated_crafting;

import net.fabricmc.api.ModInitializer;
import net.sssubtlety.automated_crafting.gui.AutoCrafterGuiDescription;

import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.END_DATA_PACK_RELOAD;

public class Init implements ModInitializer {
    @Override
    public void onInitialize() {
        Config.init();
        Registrar.init();
        AutoCrafterGuiDescription.init();

        END_DATA_PACK_RELOAD.register((u1, u2, u3) -> AutoCrafterBlockEntity.validator.update());
    }
}
