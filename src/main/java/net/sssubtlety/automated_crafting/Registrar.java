package net.sssubtlety.automated_crafting;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.sssubtlety.automated_crafting.gui.AutoCrafterGuiDescription;

import static net.sssubtlety.automated_crafting.AutomatedCrafting.NAMESPACE;

@SuppressWarnings("UnstableApiUsage")
public class Registrar {
    static void init() { }

    //     public Material(MapColor color, boolean liquid, boolean solid, boolean blocksMovement, boolean blocksLight, boolean burnable, boolean replaceable, PistonBehavior pistonBehavior) {
    public static Block BLOCK = Registry.register(Registries.BLOCK, AutoCrafterBlock.ID, new AutoCrafterBlock(AbstractBlock.Settings.create().mapColor(MapColor.GRAY).solid().pistonBehavior( PistonBehavior.BLOCK).strength(1, 3)));

    public static BlockItem ITEM = Registry.register(Registries.ITEM, new Identifier(NAMESPACE, "auto_crafter"),
            new BlockItem(Registrar.BLOCK, new Item.Settings()));


    public static BlockEntityType<AutoCrafterBlockEntity> BLOCK_ENTITY_TYPE =
            Registry.register(
                    Registries.BLOCK_ENTITY_TYPE,
                NAMESPACE + ":auto_crafter_entity",
                    FabricBlockEntityTypeBuilder.create(AutoCrafterBlockEntity::new, Registrar.BLOCK).build(null)
            );

    public static ScreenHandlerType<AutoCrafterGuiDescription> SCREEN_HANDLER_TYPE =
            ScreenHandlerRegistry.registerSimple(AutoCrafterBlock.ID, (world, playerInventory) -> AutoCrafterGuiDescription.create(world, playerInventory, ScreenHandlerContext.EMPTY));

    static {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE_BLOCKS).register(entries -> entries.addItem(ITEM));
        ItemStorage.SIDED.registerForBlockEntity(AutoCrafterBlockEntity::getStorage, BLOCK_ENTITY_TYPE);
    }
}
