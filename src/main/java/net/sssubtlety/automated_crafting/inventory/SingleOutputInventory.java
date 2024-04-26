package net.sssubtlety.automated_crafting.inventory;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SingleOutputInventory extends SimpleInventory {
    public final Storage<ItemVariant> storage;
    public SingleOutputInventory(Runnable onChange) {
        super(1);
        this.storage = new ReactiveStorage(this, null, onChange);
    }

    public ItemStack get() {
        return getStack(0);
    }

    public Item getItem() {
        return get().getItem();
    }

    public int getCount() {
        return get().getCount();
    }

    public void set(ItemStack stack) {
        setStack(0, stack);
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }

    public int getMaxCount() {
        return 64;
    }

    public ItemStack split(int i) {
        return get().split(i);
    }
}
