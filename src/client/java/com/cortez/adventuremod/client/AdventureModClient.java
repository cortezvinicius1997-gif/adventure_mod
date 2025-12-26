package com.cortez.adventuremod.client;

import com.cortez.adventuremod.block.entity.ModBlockEntities;
import com.cortez.adventuremod.client.block.entity.renderer.PedestalBlockEntityRenderer;
import com.cortez.adventuremod.client.hud.ThirstHudOverlay;
import com.cortez.adventuremod.client.network.ModNetworkingClient;
import com.cortez.adventuremod.client.screen.custom.PedestalScreen;
import com.cortez.adventuremod.client.screen.custom.ProgrammingScreen;
import com.cortez.adventuremod.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class AdventureModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(new ThirstHudOverlay());


        BlockEntityRendererFactories.register(ModBlockEntities.PEDESTAL_BE, PedestalBlockEntityRenderer::new);
        HandledScreens.register(ModScreenHandlers.PEDESTAL_SCREEN_HANDLER, PedestalScreen::new);
        HandledScreens.register(ModScreenHandlers.PROGRAMMING_SCREEN_HANDLER, ProgrammingScreen::new);


        ModNetworkingClient.registerS2CPackets();
    }
}
