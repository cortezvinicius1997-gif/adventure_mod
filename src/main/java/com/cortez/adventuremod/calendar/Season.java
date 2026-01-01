package com.cortez.adventuremod.calendar;

/**
 * Enum representando as 4 estações do ano.
 */
public enum Season {
    SPRING("Primavera", 0),
    SUMMER("Verão", 1),
    AUTUMN("Outono", 2),
    WINTER("Inverno", 3);

    private final String displayName;
    private final int index;

    Season(String displayName, int index) {
        this.displayName = displayName;
        this.index = index;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getIndex() {
        return index;
    }

    public Season next() {
        return values()[(ordinal() + 1) % values().length];
    }

    public Season previous() {
        return values()[(ordinal() - 1 + values().length) % values().length];
    }

    public static Season fromIndex(int index) {
        for (Season season : values()) {
            if (season.index == index) {
                return season;
            }
        }
        return SPRING;
    }

    /**
     * Retorna a estação baseada no dia do ano (1-112).
     * Cada estação tem 28 dias.
     */
    public static Season fromDayOfYear(int dayOfYear) {
        if (dayOfYear <= 28) return SPRING;
        if (dayOfYear <= 56) return SUMMER;
        if (dayOfYear <= 84) return AUTUMN;
        return WINTER;
    }
}

