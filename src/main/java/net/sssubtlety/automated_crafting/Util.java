package net.sssubtlety.automated_crafting;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.util.Optional;

public final class Util {
    @SuppressWarnings("ConstantConditions")
    public static int getRgb(Text text) {
        final TextColor color = text.getStyle().getColor();
        if (color == null) return Formatting.WHITE.getColorValue();
        else return color.getRgb();
    }

    public static Text replace(Text text, String regex, String replacement) {
        String string = text.getString();
        string = string.replaceAll(regex, replacement);
        return Text.literal(string).setStyle(text.getStyle());
    }

    public static boolean isModLoaded(String id, String versionPredicate) {
        final Optional<ModContainer> optModContainer = FabricLoader.getInstance().getModContainer(id);
        if (optModContainer.isPresent()){
            try {
                return VersionPredicate.parse(versionPredicate).test(optModContainer.get().getMetadata().getVersion());
            } catch (VersionParsingException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    private Util() { }
}
