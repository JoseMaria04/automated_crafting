package net.sssubtlety.automated_crafting.mixin.accessor;


import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CraftingInventory.class)
public interface CraftingInventoryAccessor {
    @Accessor("stacks")
    DefaultedList<ItemStack> automated_crafting$getStacks();

    @Accessor("stacks")
    @Mutable
    void automated_crafting$setStacks(DefaultedList<ItemStack> stacks);
}
