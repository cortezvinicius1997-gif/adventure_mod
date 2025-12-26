package com.cortez.adventuremod.client.hud;

import com.cortez.adventuremod.AdventureMod;
import com.cortez.adventuremod.config.AdventureConfig;
import com.cortez.adventuremod.util.IEntityDataSaver;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class ThirstHudOverlay implements HudRenderCallback {

    private static final Identifier THIRST_ICONS = Identifier.of(AdventureMod.MODID, "textures/gui/thirst.png");

    // coordenadas dos ícones (cheio, meio, vazio)
    private static final int ICON_EMPTY = 0;
    private static final int ICON_HALF = 8;
    private static final int ICON_FULL = 16;

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        if (client.player.isCreative()) return;
        if (!AdventureConfig.isThirst()) return;

        IEntityDataSaver data = (IEntityDataSaver) client.player;
        NbtCompound nbt = data.getPersistentData();

        int thirst = nbt.getInt("thirst");                   // 0 a 20
        float saturation = nbt.getFloat("thirst_saturation"); // 0 a 5

        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();

        // posição ao lado da barra de fome, igual vanilla
        int baseX = screenWidth / 2 + 82;
        int baseY = screenHeight - 49; // posicionado acima da fome

        // desenhar 10 ícones (cada vale 2 pontos)
        for (int i = 0; i < 10; i++) {
            int iconX = baseX - i * 8;
            int iconY = baseY;

            int u;

            int thirstLevelForIcon = thirst - i * 2;

            if (thirstLevelForIcon >= 2) {
                u = ICON_FULL;
            } else if (thirstLevelForIcon == 1) {
                u = ICON_HALF;
            } else {
                u = ICON_EMPTY;
            }

            context.drawTexture(
                    THIRST_ICONS,
                    iconX,
                    iconY,
                    u, 0,
                    8, 9,
                    24, 9
            );
        }
    }
}
