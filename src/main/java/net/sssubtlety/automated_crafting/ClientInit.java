package net.sssubtlety.automated_crafting;

import de.guntram.mcmod.crowdintranslate.CrowdinTranslate;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.sssubtlety.automated_crafting.gui.AutoCrafterGuiDescription;

import static net.fabricmc.fabric.api.resource.ResourceManagerHelper.registerBuiltinResourcePack;

public class ClientInit implements ClientModInitializer {
    public static final String POWERED = "powered";

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(Registrar.BLOCK, RenderLayer.getSolid());
        // don't be fooled, specifying the generics' types is necessary here
        HandledScreens.<AutoCrafterGuiDescription, AutoCrafterScreen>register(Registrar.SCREEN_HANDLER_TYPE,
            (gui, playerInventory, titleText) -> new AutoCrafterScreen(gui, playerInventory.player, titleText)
        );
        if (FeatureControl.shouldFetchTranslationUpdates()) CrowdinTranslate.downloadTranslations("automated-crafting", AutomatedCrafting.NAMESPACE);

        ModContainer modContainer = FabricLoader.getInstance().getModContainer(AutomatedCrafting.NAMESPACE).orElseThrow();
        registerBuiltinResourcePack(new Identifier(AutomatedCrafting.NAMESPACE, POWERED + "_icon"), modContainer, ResourcePackActivationType.NORMAL);
        registerBuiltinResourcePack(new Identifier(AutomatedCrafting.NAMESPACE, POWERED + "_block"), modContainer, ResourcePackActivationType.NORMAL);
    }
}
