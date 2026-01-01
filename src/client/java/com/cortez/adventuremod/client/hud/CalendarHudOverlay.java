package com.cortez.adventuremod.client.hud;

import com.cortez.adventuremod.calendar.Season;
import com.cortez.adventuremod.client.calendar.ClientCalendarData;
import com.cortez.adventuremod.config.AdventureConfig;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

/**
 * HUD que exibe informações do calendário na tela.
 */
public class CalendarHudOverlay implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) return;

        // Não mostrar se não estiver em um mundo
        if (client.world == null) return;

        // Não mostrar se o calendário HUD estiver desabilitado
        if (!AdventureConfig.isCalendarHudEnabled()) return;

        // Não mostrar se as opções de HUD estiverem desativadas ou F3 estiver aberto
        if (client.getDebugHud().shouldShowDebugHud()) return;

        TextRenderer textRenderer = client.textRenderer;

        // Posição no canto superior direito
        int screenWidth = context.getScaledWindowWidth();
        int x = screenWidth - 10;
        int y = 10;

        // Obtém dados do calendário
        Season season = ClientCalendarData.getSeason();
        String time = ClientCalendarData.getFormattedTime();
        String date = ClientCalendarData.getShortDateString();
        int year = ClientCalendarData.getYear();

        // Cores baseadas na estação
        int seasonColor = getSeasonColor(season);
        int timeColor = ClientCalendarData.isDaytime() ? 0xFFFF00 : 0x6666FF; // Amarelo/Azul

        // Ícone da estação
        String seasonIcon = getSeasonIcon(season);

        // Renderiza o tempo (hora)
        String timeIcon = ClientCalendarData.isDaytime() ? "☀" : "☾";
        String timeText = timeIcon + " " + time;
        int timeWidth = textRenderer.getWidth(timeText);

        // Fundo semi-transparente
        int bgPadding = 4;
        int bgX = x - timeWidth - bgPadding;
        int bgY = y - bgPadding;
        int bgWidth = timeWidth + bgPadding * 2;
        int bgHeight = textRenderer.fontHeight * 3 + bgPadding * 3;

        context.fill(bgX - bgPadding, bgY, bgX + bgWidth, bgY + bgHeight, 0x80000000);

        // Renderiza hora
        context.drawTextWithShadow(textRenderer, timeText, x - timeWidth, y, timeColor);
        y += textRenderer.fontHeight + 2;

        // Renderiza data
        String dateText = "📅 " + date;
        int dateWidth = textRenderer.getWidth(dateText);
        context.drawTextWithShadow(textRenderer, dateText, x - dateWidth, y, 0xFFFFFF);
        y += textRenderer.fontHeight + 2;

        // Renderiza estação e ano
        String seasonText = seasonIcon + " " + season.getDisplayName() + " | Ano " + year;
        int seasonWidth = textRenderer.getWidth(seasonText);
        context.drawTextWithShadow(textRenderer, seasonText, x - seasonWidth, y, seasonColor);
    }

    private int getSeasonColor(Season season) {
        return switch (season) {
            case SPRING -> 0x90EE90; // Verde claro
            case SUMMER -> 0xFFD700; // Dourado
            case AUTUMN -> 0xFF8C00; // Laranja
            case WINTER -> 0x87CEEB; // Azul claro
        };
    }

    private String getSeasonIcon(Season season) {
        return switch (season) {
            case SPRING -> "🌸";
            case SUMMER -> "☀";
            case AUTUMN -> "🍂";
            case WINTER -> "❄";
        };
    }
}

