package net.sssubtlety.automated_crafting;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.Set;

import static net.sssubtlety.automated_crafting.AutomatedCrafting.id;

public class Registrar {
	public static final ResourceKey<Block> BLOCK_KEY = ResourceKey.create(Registries.BLOCK, AutoCrafterBlock.ID);
	public static final ResourceKey<Item> ITEM_KEY = ResourceKey.create(Registries.ITEM, AutoCrafterBlock.ID);

	public static final Block BLOCK = Registry.register(
		BuiltInRegistries.BLOCK,
		BLOCK_KEY,
		new AutoCrafterBlock(BlockBehaviour.Properties.of()
			.setId(BLOCK_KEY)
			.mapColor(MapColor.STONE)
			.pushReaction(PushReaction.IMMOVEABLE)
			.strength(1, 3)
		)
	);

	public static final BlockItem ITEM = Registry.register(
		BuiltInRegistries.ITEM,
		ITEM_KEY,
		new BlockItem(BLOCK, new Item.Properties().setId(ITEM_KEY).useBlockDescriptionPrefix())
	);

	// same id as previous versions, so existing worlds keep their auto crafters' contents
	public static final BlockEntityType<AutoCrafterBlockEntity> BLOCK_ENTITY_TYPE = Registry.register(
		BuiltInRegistries.BLOCK_ENTITY_TYPE,
		id("auto_crafter_entity"),
		new BlockEntityType<>(AutoCrafterBlockEntity::new, Set.of(BLOCK))
	);

	// the opening data tells the client which layout (simple or complex) the server uses
	public static final ExtendedMenuType<AutoCrafterMenu, Boolean> MENU_TYPE = Registry.register(
		BuiltInRegistries.MENU,
		AutoCrafterBlock.ID,
		new ExtendedMenuType<>(AutoCrafterMenu::new, ByteBufCodecs.BOOL)
	);

	static void init() {
		Item.BY_BLOCK.put(BLOCK, ITEM);
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.REDSTONE_BLOCKS)
			.register(output -> output.insertAfter(Items.CRAFTER, ITEM));
	}
}
