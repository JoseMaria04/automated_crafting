package net.sssubtlety.automated_crafting;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutomatedCrafting {
	public static final String NAMESPACE = "automated_crafting";
	public static final Component NAME = Component.translatable("text." + NAMESPACE + ".name");
	public static final Logger LOGGER = LoggerFactory.getLogger(NAMESPACE);

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(NAMESPACE, path);
	}
}
