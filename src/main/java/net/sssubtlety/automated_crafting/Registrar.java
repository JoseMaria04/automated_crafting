package net.sssubtlety.automated_crafting;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.sssubtlety.automated_crafting.gui.AutoCrafterGuiDescription;

import static net.sssubtlety.automated_crafting.AutomatedCrafting.NAMESPACE;

@SuppressWarnings("UnstableApiUsage")
public class Registrar {
    static void init() { }

    public static Block BLOCK = Registry.register(Registry.BLOCK, AutoCrafterBlock.ID, new AutoCrafterBlock(FabricBlockSettings.of(new Material(MapColor.GRAY, false, true, true, true, false, false, PistonBehavior.BLOCK)).strength(1, 3)));

    public static BlockItem ITEM = Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "auto_crafter"),
            new BlockItem(Registrar.BLOCK, new Item.Settings().group(ItemGroup.REDSTONE)));

    public static BlockEntityType<AutoCrafterBlockEntity> BLOCK_ENTITY_TYPE =
            Registry.register(
                    Registry.BLOCK_ENTITY_TYPE,
                NAMESPACE + ":auto_crafter_entity",
                    FabricBlockEntityTypeBuilder.create(AutoCrafterBlockEntity::new, Registrar.BLOCK).build(null)
            );

    public static ScreenHandlerType<AutoCrafterGuiDescription> SCREEN_HANDLER_TYPE =
            ScreenHandlerRegistry.registerSimple(AutoCrafterBlock.ID, (world, playerInventory) -> AutoCrafterGuiDescription.create(world, playerInventory, ScreenHandlerContext.EMPTY));

    static {
        ItemStorage.SIDED.registerForBlockEntity(AutoCrafterBlockEntity::getStorage, BLOCK_ENTITY_TYPE);
    }
}
