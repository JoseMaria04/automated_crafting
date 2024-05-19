package net.sssubtlety.automated_crafting;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import org.jetbrains.annotations.Nullable;

import static net.sssubtlety.automated_crafting.Util.isModLoaded;

public class FeatureControl {
    private static final @Nullable Config CONFIG_INSTANCE;

    static {
        if (isModLoaded("cloth-config", ">=7.0.72")) {
            CONFIG_INSTANCE = AutoConfig.register(Config.class, GsonConfigSerializer::new).getConfig();
        } else CONFIG_INSTANCE = null;
    }

    public interface Defaults {
        boolean simple_mode = true;
        boolean quasi_connective = false;
        boolean redirect_redstone = false;
        boolean craft_continuously = false;
        boolean comparator_reads_output = false;
        boolean fetch_translation_updates = true;
    }

    public static boolean isSimpleMode() {
        return CONFIG_INSTANCE == null ? Defaults.simple_mode : CONFIG_INSTANCE.simple_mode;
    }

    public static boolean isQuasiConnective() {
        return CONFIG_INSTANCE == null ? Defaults.quasi_connective : CONFIG_INSTANCE.quasi_connective;
    }

    public static boolean shouldCraftContinuously() {
        return CONFIG_INSTANCE == null ? Defaults.craft_continuously : CONFIG_INSTANCE.craft_continuously;
    }

    public static boolean shouldRedirectRedstone() {
        return CONFIG_INSTANCE == null ? Defaults.redirect_redstone : CONFIG_INSTANCE.redirect_redstone;
    }

    public static boolean shouldComparatorReadOutput() {
        return CONFIG_INSTANCE == null ? Defaults.comparator_reads_output : CONFIG_INSTANCE.comparator_reads_output;
    }


    public static boolean shouldFetchTranslationUpdates() {
        return CONFIG_INSTANCE == null ? Defaults.fetch_translation_updates : CONFIG_INSTANCE.fetch_translation_updates;
    }

    public static boolean isConfigLoaded() {
        return CONFIG_INSTANCE != null;
    }

    public static void init() { }

    private FeatureControl() { }
}
