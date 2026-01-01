package com.cortez.adventuremod.calendar;

import net.minecraft.nbt.NbtCompound;

/**
 * Representa uma data no calendário do mod.
 *
 * Estrutura do calendário:
 * - 1 ano = 4 estações
 * - 1 estação = 28 dias (4 semanas)
 * - 1 semana = 7 dias
 * - 1 dia = 24000 ticks do Minecraft (20 minutos reais)
 *
 * Total: 1 ano = 112 dias = 2.688.000 ticks
 */
public class CalendarDate {

    public static final int TICKS_PER_DAY = 24000;
    public static final int DAYS_PER_WEEK = 7;
    public static final int DAYS_PER_SEASON = 28;
    public static final int SEASONS_PER_YEAR = 4;
    public static final int DAYS_PER_YEAR = DAYS_PER_SEASON * SEASONS_PER_YEAR; // 112 dias
    public static final long TICKS_PER_YEAR = (long) DAYS_PER_YEAR * TICKS_PER_DAY;

    private int year;
    private int dayOfYear; // 1-112
    private int hour; // 0-23
    private int minute; // 0-59

    public CalendarDate() {
        this.year = 1;
        this.dayOfYear = 1;
        this.hour = 6; // Manhã (6:00)
        this.minute = 0;
    }

    public CalendarDate(int year, int dayOfYear, int hour, int minute) {
        this.year = year;
        this.dayOfYear = Math.max(1, Math.min(dayOfYear, DAYS_PER_YEAR));
        this.hour = Math.max(0, Math.min(hour, 23));
        this.minute = Math.max(0, Math.min(minute, 59));
    }

    /**
     * Cria uma CalendarDate a partir dos ticks do mundo do Minecraft.
     */
    public static CalendarDate fromWorldTime(long worldTime) {
        CalendarDate date = new CalendarDate();
        date.updateFromWorldTime(worldTime);
        return date;
    }

    /**
     * Atualiza a data baseado no tempo do mundo.
     */
    public void updateFromWorldTime(long worldTime) {
        // Calcula o dia total desde o início
        long totalDays = worldTime / TICKS_PER_DAY;

        // Calcula o ano (começando do ano 1)
        this.year = (int) (totalDays / DAYS_PER_YEAR) + 1;

        // Calcula o dia do ano (1-112)
        this.dayOfYear = (int) (totalDays % DAYS_PER_YEAR) + 1;

        // Calcula a hora do dia
        int ticksInDay = (int) (worldTime % TICKS_PER_DAY);

        // No Minecraft: 0 = 6:00, 6000 = meio-dia, 12000 = 18:00, 18000 = meia-noite
        int minecraftHour = ((ticksInDay / 1000) + 6) % 24;
        int minecraftMinute = (int) ((ticksInDay % 1000) * 60 / 1000);

        this.hour = minecraftHour;
        this.minute = minecraftMinute;
    }

    /**
     * Retorna a estação atual.
     */
    public Season getSeason() {
        return Season.fromDayOfYear(dayOfYear);
    }

    /**
     * Retorna o dia da estação atual (1-28).
     */
    public int getDayOfSeason() {
        return ((dayOfYear - 1) % DAYS_PER_SEASON) + 1;
    }

    /**
     * Retorna a semana da estação (1-4).
     */
    public int getWeekOfSeason() {
        return ((getDayOfSeason() - 1) / DAYS_PER_WEEK) + 1;
    }

    /**
     * Retorna o dia da semana (1-7, onde 1 = Segunda).
     */
    public int getDayOfWeek() {
        return ((dayOfYear - 1) % DAYS_PER_WEEK) + 1;
    }

    /**
     * Retorna o nome do dia da semana.
     */
    public String getDayOfWeekName() {
        return switch (getDayOfWeek()) {
            case 1 -> "Segunda";
            case 2 -> "Terça";
            case 3 -> "Quarta";
            case 4 -> "Quinta";
            case 5 -> "Sexta";
            case 6 -> "Sábado";
            case 7 -> "Domingo";
            default -> "Desconhecido";
        };
    }

    /**
     * Verifica se é dia ou noite.
     */
    public boolean isDaytime() {
        return hour >= 6 && hour < 18;
    }

    /**
     * Verifica se é manhã (6:00-12:00).
     */
    public boolean isMorning() {
        return hour >= 6 && hour < 12;
    }

    /**
     * Verifica se é tarde (12:00-18:00).
     */
    public boolean isAfternoon() {
        return hour >= 12 && hour < 18;
    }

    /**
     * Verifica se é noite (18:00-6:00).
     */
    public boolean isNight() {
        return hour >= 18 || hour < 6;
    }

    /**
     * Retorna uma string formatada da hora (HH:MM).
     */
    public String getFormattedTime() {
        return String.format("%02d:%02d", hour, minute);
    }

    /**
     * Retorna uma string formatada da data completa.
     */
    public String getFormattedDate() {
        return String.format("Ano %d, %s, Dia %d (%s)",
            year,
            getSeason().getDisplayName(),
            getDayOfSeason(),
            getDayOfWeekName());
    }

    /**
     * Retorna uma string formatada completa com data e hora.
     */
    public String getFormattedDateTime() {
        return getFormattedDate() + " - " + getFormattedTime();
    }

    // Getters
    public int getYear() { return year; }
    public int getDayOfYear() { return dayOfYear; }
    public int getHour() { return hour; }
    public int getMinute() { return minute; }

    // Setters
    public void setYear(int year) { this.year = Math.max(1, year); }
    public void setDayOfYear(int dayOfYear) {
        this.dayOfYear = Math.max(1, Math.min(dayOfYear, DAYS_PER_YEAR));
    }

    /**
     * Salva a data em NBT.
     */
    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("year", year);
        nbt.putInt("dayOfYear", dayOfYear);
        nbt.putInt("hour", hour);
        nbt.putInt("minute", minute);
        return nbt;
    }

    /**
     * Carrega a data de NBT.
     */
    public static CalendarDate fromNbt(NbtCompound nbt) {
        return new CalendarDate(
            nbt.getInt("year"),
            nbt.getInt("dayOfYear"),
            nbt.getInt("hour"),
            nbt.getInt("minute")
        );
    }

    @Override
    public String toString() {
        return getFormattedDateTime();
    }
}

