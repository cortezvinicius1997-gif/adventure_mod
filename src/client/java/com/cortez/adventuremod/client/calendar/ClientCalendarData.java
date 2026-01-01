package com.cortez.adventuremod.client.calendar;

import com.cortez.adventuremod.calendar.CalendarDate;
import com.cortez.adventuremod.calendar.Season;
import com.cortez.adventuremod.network.CalendarSyncPayload;

/**
 * Armazena os dados do calendário no lado do cliente.
 * Sincronizado pelo servidor periodicamente.
 */
public class ClientCalendarData {

    private static int year = 1;
    private static int dayOfYear = 1;
    private static int hour = 6;
    private static int minute = 0;
    private static Season season = Season.SPRING;

    /**
     * Atualiza os dados do calendário com base no payload recebido do servidor.
     */
    public static void update(CalendarSyncPayload payload) {
        year = payload.year();
        dayOfYear = payload.dayOfYear();
        hour = payload.hour();
        minute = payload.minute();
        season = Season.fromIndex(payload.seasonIndex());
    }

    public static int getYear() {
        return year;
    }

    public static int getDayOfYear() {
        return dayOfYear;
    }

    public static int getHour() {
        return hour;
    }

    public static int getMinute() {
        return minute;
    }

    public static Season getSeason() {
        return season;
    }

    /**
     * Retorna o dia da estação (1-28).
     */
    public static int getDayOfSeason() {
        return ((dayOfYear - 1) % CalendarDate.DAYS_PER_SEASON) + 1;
    }

    /**
     * Retorna o nome do dia da semana.
     */
    public static String getDayOfWeekName() {
        int dayOfWeek = ((dayOfYear - 1) % CalendarDate.DAYS_PER_WEEK) + 1;
        return switch (dayOfWeek) {
            case 1 -> "Seg";
            case 2 -> "Ter";
            case 3 -> "Qua";
            case 4 -> "Qui";
            case 5 -> "Sex";
            case 6 -> "Sáb";
            case 7 -> "Dom";
            default -> "???";
        };
    }

    /**
     * Retorna a hora formatada (HH:MM).
     */
    public static String getFormattedTime() {
        return String.format("%02d:%02d", hour, minute);
    }

    /**
     * Verifica se é dia.
     */
    public static boolean isDaytime() {
        return hour >= 6 && hour < 18;
    }

    /**
     * Retorna uma string formatada curta da data.
     */
    public static String getShortDateString() {
        return String.format("%s %d/%d", getDayOfWeekName(), getDayOfSeason(), season.getIndex() + 1);
    }
}

