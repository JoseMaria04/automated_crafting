package net.sssubtlety.automated_crafting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import static net.sssubtlety.automated_crafting.AutomatedCrafting.LOGGER;
import static net.sssubtlety.automated_crafting.AutomatedCrafting.NAMESPACE;

/**
 * Reads {@code config/automated_crafting.json}. The file layout is the same one Cloth Config's AutoConfig
 * wrote in previous versions, so existing configs keep working without Cloth Config installed.
 */
public final class FeatureControl {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static Config config = new Config();

	public static final class Config {
		boolean simple_mode = true;
		boolean quasi_connective = false;
		boolean redirect_redstone = false;
		boolean craft_continuously = false;
		boolean comparator_reads_output = false;
	}

	public static void init() {
		final Path path = FabricLoader.getInstance().getConfigDir().resolve(NAMESPACE + ".json");
		if (Files.exists(path)) {
			try (Reader reader = Files.newBufferedReader(path)) {
				final Config read = GSON.fromJson(reader, Config.class);
				if (read != null) config = read;
			} catch (IOException | RuntimeException e) {
				LOGGER.error("Failed to read {}, using defaults", path, e);
			}
		}

		try (Writer writer = Files.newBufferedWriter(path)) {
			GSON.toJson(config, writer);
		} catch (IOException e) {
			LOGGER.error("Failed to write {}", path, e);
		}
	}

	public static boolean isSimpleMode() {
		return config.simple_mode;
	}

	public static boolean isQuasiConnective() {
		return config.quasi_connective;
	}

	public static boolean shouldCraftContinuously() {
		return config.craft_continuously;
	}

	public static boolean shouldRedirectRedstone() {
		return config.redirect_redstone;
	}

	public static boolean shouldComparatorReadOutput() {
		return config.comparator_reads_output;
	}

	private FeatureControl() { }
}
