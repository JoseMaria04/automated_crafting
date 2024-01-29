package net.sssubtlety.automated_crafting;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import static net.sssubtlety.automated_crafting.AutomatedCrafting.NAMESPACE;

@me.shedaniel.autoconfig.annotation.Config(name = NAMESPACE)
public class Config implements ConfigData {
    @ConfigEntry.Gui.Tooltip()
    boolean simple_mode = FeatureControl.Defaults.simple_mode;

    @ConfigEntry.Gui.Tooltip()
    boolean quasi_connective = FeatureControl.Defaults.quasi_connective;

    @ConfigEntry.Gui.Tooltip()
    boolean redirect_redstone = FeatureControl.Defaults.redirect_redstone;

    @ConfigEntry.Gui.Tooltip()
    boolean craft_continuously = FeatureControl.Defaults.craft_continuously;

    @ConfigEntry.Gui.Tooltip()
    boolean comparator_reads_output = FeatureControl.Defaults.comparator_reads_output;

    @ConfigEntry.Gui.Tooltip()
    boolean fetch_translation_updates = FeatureControl.Defaults.fetch_translation_updates;
}
