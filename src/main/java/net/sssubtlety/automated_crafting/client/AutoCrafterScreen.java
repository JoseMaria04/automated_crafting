package net.sssubtlety.automated_crafting.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.sssubtlety.automated_crafting.AutoCrafterMenu;

import static net.sssubtlety.automated_crafting.AutoCrafterBlockEntity.GRID_WIDTH;
import static net.sssubtlety.automated_crafting.AutoCrafterMenu.*;
import static net.sssubtlety.automated_crafting.AutomatedCrafting.NAMESPACE;

public class AutoCrafterScreen extends AbstractContainerScreen<AutoCrafterMenu> {
	private static final Identifier SLOT_SPRITE = Identifier.withDefaultNamespace("container/slot");
	private static final Component TEMPLATE_LABEL = Component.translatable("label." + NAMESPACE + ".template");
	private static final Component INPUT_LABEL = Component.translatable("label." + NAMESPACE + ".input");

	private static final int PANEL = 0xFFC6C6C6;
	private static final int LIGHT = 0xFFFFFFFF;
	private static final int DARK = 0xFF555555;
	private static final int OUTLINE = 0xFF000000;
	private static final int LABEL_COLOR = 0xFF404040;
	private static final int TEMPLATE_TINT = 0x608B8B8B;

	public AutoCrafterScreen(AutoCrafterMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title, 176, IMAGE_HEIGHT);
		this.inventoryLabelY = this.imageHeight - 94;
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		super.extractBackground(graphics, mouseX, mouseY, a);
		final int x0 = this.leftPos;
		final int y0 = this.topPos;
		final int x1 = x0 + this.imageWidth;
		final int y1 = y0 + this.imageHeight;

		// vanilla-style panel
		graphics.fill(x0 + 1, y0, x1 - 1, y1, OUTLINE);
		graphics.fill(x0, y0 + 1, x1, y1 - 1, OUTLINE);
		graphics.fill(x0 + 1, y0 + 1, x1 - 1, y1 - 1, PANEL);
		graphics.fill(x0 + 1, y0 + 1, x1 - 3, y0 + 3, LIGHT);
		graphics.fill(x0 + 1, y0 + 1, x0 + 3, y1 - 3, LIGHT);
		graphics.fill(x0 + 3, y1 - 3, x1 - 1, y1 - 1, DARK);
		graphics.fill(x1 - 3, y0 + 3, x1 - 1, y1 - 1, DARK);

		for (Slot slot : this.menu.slots) {
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_SPRITE, x0 + slot.x - 1, y0 + slot.y - 1, 18, 18);
		}

		// arrow towards the output
		final int outputX = this.menu.isSimpleMode() ? SIMPLE_OUTPUT_X : COMPLEX_OUTPUT_X;
		final int gridEnd = (this.menu.isSimpleMode() ? SIMPLE_INPUT_X : COMPLEX_INPUT_X) + GRID_WIDTH * SLOT_SIZE;
		final int arrowY = y0 + OUTPUT_Y + 8;
		final int arrowStart = x0 + gridEnd + 2;
		final int arrowEnd = x0 + outputX - 4;
		graphics.fill(arrowStart, arrowY - 1, arrowEnd - 3, arrowY + 1, DARK);
		for (int i = 0; i < 4; i++) graphics.fill(arrowEnd - 4 + i, arrowY - 4 + i, arrowEnd - 3 + i, arrowY + 4 - i, DARK);
	}

	@Override
	protected void extractSlot(GuiGraphicsExtractor graphics, Slot slot, int mouseX, int mouseY) {
		super.extractSlot(graphics, slot, mouseX, mouseY);
		// grey out template items so they read as ghosts
		if (slot instanceof AutoCrafterMenu.TemplateSlot && slot.hasItem()) {
			graphics.fill(slot.x, slot.y, slot.x + 16, slot.y + 16, TEMPLATE_TINT);
		}
	}

	@Override
	protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
		super.extractLabels(graphics, xm, ym);
		final int gridWidth = GRID_WIDTH * SLOT_SIZE;
		if (this.menu.isSimpleMode()) {
			centered(graphics, TEMPLATE_LABEL, SIMPLE_TEMPLATE_X + gridWidth / 2);
			centered(graphics, INPUT_LABEL, SIMPLE_INPUT_X + gridWidth / 2);
		}
	}

	private void centered(GuiGraphicsExtractor graphics, Component text, int centerX) {
		graphics.text(this.font, text, centerX - this.font.width(text) / 2, GRID_LABEL_Y, LABEL_COLOR, false);
	}
}
