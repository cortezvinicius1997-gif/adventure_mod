package com.cortez.adventuremod.calendar;

import net.minecraft.world.biome.Biome;

/**
 * Efeitos climáticos baseados na estação atual.
 * Usado para modificar comportamentos do jogo de acordo com a estação.
 */
public class SeasonEffects {

    /**
     * Retorna o modificador de temperatura para a estação.
     * Valores: -1.0 (muito frio) a 1.0 (muito quente)
     */
    public static float getTemperatureModifier(Season season) {
        return switch (season) {
            case SPRING -> 0.0f;   // Temperatura neutra
            case SUMMER -> 0.5f;   // Mais quente
            case AUTUMN -> -0.2f;  // Levemente frio
            case WINTER -> -0.8f;  // Muito frio
        };
    }

    /**
     * Retorna o modificador de taxa de crescimento de plantas.
     * Valores: 0.0 (sem crescimento) a 2.0 (crescimento dobrado)
     */
    public static float getCropGrowthModifier(Season season) {
        return switch (season) {
            case SPRING -> 1.5f;   // Crescimento acelerado
            case SUMMER -> 1.2f;   // Crescimento normal-rápido
            case AUTUMN -> 0.8f;   // Crescimento lento
            case WINTER -> 0.3f;   // Crescimento muito lento
        };
    }

    /**
     * Retorna a chance de chuva para a estação (0.0 a 1.0).
     */
    public static float getRainChance(Season season) {
        return switch (season) {
            case SPRING -> 0.4f;   // Chuvas frequentes
            case SUMMER -> 0.15f;  // Chuvas raras
            case AUTUMN -> 0.5f;   // Chuvas frequentes
            case WINTER -> 0.6f;   // Neve frequente
        };
    }

    /**
     * Retorna o modificador de duração do dia.
     * Valores: 0.8 (dia curto) a 1.2 (dia longo)
     */
    public static float getDaylightModifier(Season season) {
        return switch (season) {
            case SPRING -> 1.0f;   // Dia normal
            case SUMMER -> 1.2f;   // Dia mais longo
            case AUTUMN -> 0.9f;   // Dia levemente curto
            case WINTER -> 0.7f;   // Dia curto
        };
    }

    /**
     * Retorna o modificador de spawn de mobs hostis.
     * Valores maiores = mais mobs
     */
    public static float getMobSpawnModifier(Season season) {
        return switch (season) {
            case SPRING -> 1.0f;
            case SUMMER -> 0.8f;   // Menos mobs (dias longos)
            case AUTUMN -> 1.1f;
            case WINTER -> 1.3f;   // Mais mobs (noites longas)
        };
    }

    /**
     * Retorna o modificador de sede/desidratação.
     */
    public static float getThirstModifier(Season season) {
        return switch (season) {
            case SPRING -> 1.0f;
            case SUMMER -> 1.5f;   // Sede aumentada no verão
            case AUTUMN -> 0.9f;
            case WINTER -> 0.7f;   // Menos sede no inverno
        };
    }

    /**
     * Verifica se deveria nevar em vez de chover baseado na estação e temperatura do bioma.
     */
    public static boolean shouldSnowInstead(Season season, Biome biome) {
        if (season == Season.WINTER) {
            return true; // Sempre neve no inverno
        }
        return false;
    }

    /**
     * Retorna a cor de folhagem modificada pela estação.
     * Retorna um valor ARGB.
     */
    public static int getFoliageColorModifier(Season season, int originalColor) {
        return switch (season) {
            case SPRING -> blendColors(originalColor, 0xFF90EE90, 0.3f); // Verde claro
            case SUMMER -> originalColor; // Cor original
            case AUTUMN -> blendColors(originalColor, 0xFFFF8C00, 0.5f); // Laranja/marrom
            case WINTER -> blendColors(originalColor, 0xFFB0B0B0, 0.6f); // Cinza (sem folhas)
        };
    }

    /**
     * Mistura duas cores.
     */
    private static int blendColors(int color1, int color2, float ratio) {
        int a1 = (color1 >> 24) & 0xFF;
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = color1 & 0xFF;

        int a2 = (color2 >> 24) & 0xFF;
        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = color2 & 0xFF;

        int a = (int) (a1 * (1 - ratio) + a2 * ratio);
        int r = (int) (r1 * (1 - ratio) + r2 * ratio);
        int g = (int) (g1 * (1 - ratio) + g2 * ratio);
        int b = (int) (b1 * (1 - ratio) + b2 * ratio);

        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}

