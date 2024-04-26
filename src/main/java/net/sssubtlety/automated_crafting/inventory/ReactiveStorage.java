package net.sssubtlety.automated_crafting.inventory;

import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class ReactiveStorage extends SnapshotParticipant<DefaultedList<ItemStack>> implements Storage<ItemVariant> {
    private final Storage<ItemVariant> delegate;
    private final Inventory inventory;
    private final Runnable onChange;

    public ReactiveStorage(Inventory inventory, @Nullable Direction direction, Runnable onChange) {
        this.delegate = InventoryStorage.of(inventory, direction);
        this.inventory = inventory;
        this.onChange = onChange;
    }

    @Override
    public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        return delegate.insert(resource, maxAmount, transaction);
    }

    @Override
    public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        return delegate.extract(resource, maxAmount, transaction);
    }

    @Override
    public @NotNull Iterator<StorageView<ItemVariant>> iterator() {
        return delegate.iterator();
    }

    @Override
    protected DefaultedList<ItemStack> createSnapshot() {
        final int size = inventory.size();
        DefaultedList<ItemStack> stacks = DefaultedList.ofSize(size, ItemStack.EMPTY);
        for (int i = 0; i < size; i++) stacks.set(i, inventory.getStack(i));

        return stacks;
    }

    @Override
    protected void readSnapshot(DefaultedList<ItemStack> snapshot) {
        final int size = inventory.size();
        if (size != snapshot.size()) throw new IllegalStateException("Snapshot size does not match inventory size!");
        for (int i = 0; i < size; i++) inventory.setStack(i, snapshot.get(i));
    }

    @Override
    protected void onFinalCommit() {
        onChange.run();
    }
}
