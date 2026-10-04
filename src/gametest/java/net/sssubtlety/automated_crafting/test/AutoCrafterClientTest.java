package net.sssubtlety.automated_crafting.test;

import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.sssubtlety.automated_crafting.AutoCrafterBlockEntity;
import net.sssubtlety.automated_crafting.client.AutoCrafterScreen;

public class AutoCrafterClientTest implements FabricClientGameTest {
	@Override
	public void runTest(ClientGameTestContext context) {
		try (TestSingleplayerContext singleplayer = context.worldBuilder().create()) {
			singleplayer.getConnection().waitForChunksRender();
			singleplayer.getServer().runCommand("setblock ~2 ~ ~ automated_crafting:auto_crafter{Items:[{Slot:0b,id:\"minecraft:oak_planks\",count:1},{Slot:3b,id:\"minecraft:oak_planks\",count:1},{Slot:9b,id:\"minecraft:oak_planks\",count:1},{Slot:18b,id:\"minecraft:stick\",count:12}]}");
			singleplayer.getServer().runCommand("give @a automated_crafting:auto_crafter 3");
			singleplayer.getServer().runOnServer(server -> {
				ServerPlayer player = server.getPlayerList().getPlayers().getFirst();
				BlockPos pos = player.blockPosition().east(2);
				if (!(player.level().getBlockEntity(pos) instanceof AutoCrafterBlockEntity be)) {
					throw new AssertionError("auto crafter block entity missing at " + pos);
				}
				player.openMenu(be);
			});
			context.waitForScreen(AutoCrafterScreen.class);
			context.waitTicks(5);
			context.takeScreenshot("auto_crafter_screen");
			context.setScreen(() -> null);
			context.waitTicks(5);
			context.takeScreenshot("auto_crafter_world");
		}
	}
}
