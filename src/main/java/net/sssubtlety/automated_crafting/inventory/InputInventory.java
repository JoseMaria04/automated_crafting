package net.sssubtlety.automated_crafting.inventory;

import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.sssubtlety.automated_crafting.Config;

import static net.sssubtlety.automated_crafting.AutoCrafterBlockEntity.templatePredicate;

@SuppressWarnings("UnstableApiUsage")
public class InputInventory extends CraftingView {
    protected final Inventory templateInventory;
    public final Storage<ItemVariant> storage;

    public InputInventory(DefaultedList<ItemStack> stacks, Inventory templateInventory, Runnable onChange) {
        super(stacks);
        this.templateInventory = templateInventory;
        this.storage = new ReactiveStorage(this, null, onChange);
    }

    public int getComparatorOutput() {
        return occupiedSlots.size();
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return super.isValid(slot, stack) && matchesTemplate(slot, stack);
    }

    protected boolean matchesTemplate(int slot, ItemStack inputStack) {
        boolean matches = templatePredicate(inputStack, this.templateInventory.getStack(slot));
        return !Config.isSimpleMode() || matches;
    }
}
