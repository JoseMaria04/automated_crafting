package net.sssubtlety.automated_crafting;

import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AutomatedCrafting {
	public static final String NAMESPACE = "automated_crafting";
	public static final Text NAME = Text.translatable("text." + NAMESPACE + ".name");
	public static final Logger LOGGER = LogManager.getLogger();
}
