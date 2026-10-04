package net.sssubtlety.automated_crafting;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import static net.sssubtlety.automated_crafting.AutoCrafterBlockEntity.*;

public class AutoCrafterMenu extends AbstractContainerMenu {
	public static final int SLOT_SIZE = 18;
	public static final int GRID_Y = 18;
	public static final int GRID_LABEL_Y = GRID_Y + GRID_HEIGHT * SLOT_SIZE + 2;
	public static final int OUTPUT_Y = GRID_Y + SLOT_SIZE;
	public static final int PLAYER_INVENTORY_Y = 102;
	public static final int IMAGE_HEIGHT = PLAYER_INVENTORY_Y + 82;

	public static final int SIMPLE_TEMPLATE_X = 8;
	public static final int SIMPLE_INPUT_X = 80;
	public static final int SIMPLE_OUTPUT_X = 148;
	public static final int COMPLEX_INPUT_X = 44;
	public static final int COMPLEX_OUTPUT_X = 124;

	private final Container container;
	private final boolean simpleMode;
	private final int crafterSlotCount;

	// client side
	public AutoCrafterMenu(int containerId, Inventory inventory, Boolean simpleMode) {
		this(containerId, inventory, new SimpleContainer(INVENTORY_SIZE), simpleMode);
	}

	public AutoCrafterMenu(int containerId, Inventory inventory, Container container, boolean simpleMode) {
		super(Registrar.MENU_TYPE, containerId);
		checkContainerSize(container, INVENTORY_SIZE);
		this.container = container;
		this.simpleMode = simpleMode;
		container.startOpen(inventory.player);

		if (simpleMode) {
			addGrid(TEMPLATE_START, SIMPLE_TEMPLATE_X, true);
			addGrid(INPUT_START, SIMPLE_INPUT_X, false);
			this.addSlot(new OutputSlot(container, SIMPLE_OUTPUT_X, OUTPUT_Y));
		} else {
			addGrid(INPUT_START, COMPLEX_INPUT_X, false);
			this.addSlot(new OutputSlot(container, COMPLEX_OUTPUT_X, OUTPUT_Y));
		}

		this.crafterSlotCount = this.slots.size();
		this.addStandardInventorySlots(inventory, 8, PLAYER_INVENTORY_Y);
	}

	private void addGrid(int start, int x, boolean template) {
		for (int row = 0; row < GRID_HEIGHT; row++) {
			for (int column = 0; column < GRID_WIDTH; column++) {
				final int index = start + column + row * GRID_WIDTH;
				final int slotX = x + column * SLOT_SIZE;
				final int slotY = GRID_Y + row * SLOT_SIZE;
				this.addSlot(template ? new TemplateSlot(container, index, slotX, slotY) : new InputSlot(container, index, slotX, slotY));
			}
		}
	}

	public boolean isSimpleMode() {
		return simpleMode;
	}

	@Override
	public void clicked(int slotIndex, int buttonNum, ContainerInput input, Player player) {
		if (slotIndex >= 0 && slotIndex < this.slots.size() && this.slots.get(slotIndex) instanceof TemplateSlot templateSlot) {
			// the template holds ghost items: clicking copies the carried item without consuming it
			switch (input) {
				case PICKUP -> {
					final ItemStack carried = getCarried();
					templateSlot.set(carried.isEmpty() ? ItemStack.EMPTY : carried.copyWithCount(1));
				}
				case QUICK_MOVE, THROW -> templateSlot.set(ItemStack.EMPTY);
				default -> { }
			}
			return;
		}

		super.clicked(slotIndex, buttonNum, input, player);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		final Slot slot = this.slots.get(slotIndex);
		if (!slot.hasItem() || slot instanceof TemplateSlot) return ItemStack.EMPTY;

		final ItemStack stack = slot.getItem();
		final ItemStack original = stack.copy();
		if (slotIndex < crafterSlotCount) {
			if (!this.moveItemStackTo(stack, crafterSlotCount, this.slots.size(), true)) return ItemStack.EMPTY;
		} else {
			// in simple mode items only go in through the template, so shift-clicking into the crafter is disabled
			if (simpleMode) return ItemStack.EMPTY;
			if (!this.moveItemStackTo(stack, 0, GRID_SIZE, false)) return ItemStack.EMPTY;
		}

		if (stack.isEmpty()) slot.setByPlayer(ItemStack.EMPTY);
		else slot.setChanged();

		if (stack.getCount() == original.getCount()) return ItemStack.EMPTY;
		slot.onTake(player, stack);
		return original;
	}

	@Override
	public boolean stillValid(Player player) {
		return container.stillValid(player);
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		container.stopOpen(player);
	}

	public static class TemplateSlot extends Slot {
		public TemplateSlot(Container container, int slot, int x, int y) {
			super(container, slot, x, y);
		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			return false;
		}

		@Override
		public boolean mayPickup(Player player) {
			return false;
		}

		@Override
		public int getMaxStackSize() {
			return 1;
		}
	}

	public class InputSlot extends Slot {
		public InputSlot(Container container, int slot, int x, int y) {
			super(container, slot, x, y);
		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			if (hasItem()) return false;
			return !simpleMode || templateMatches(stack, this.container.getItem(getContainerSlot() - INPUT_START));
		}

		@Override
		public int getMaxStackSize() {
			return MAX_INPUT_STACK_SIZE;
		}

		@Override
		public boolean mayPickup(Player player) {
			return true;
		}
	}

	public static class OutputSlot extends Slot {
		public OutputSlot(Container container, int x, int y) {
			super(container, OUTPUT_SLOT, x, y);
		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			return false;
		}

		@Override
		public int getMaxStackSize() {
			return 99;
		}
	}
}
