package com.cortez.adventuremod.calendar;

import com.cortez.adventuremod.AdventureMod;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Gerenciador principal do sistema de calendário.
 * Persiste os dados do calendário e gerencia eventos de mudança de estação/dia.
 */
public class CalendarManager extends PersistentState {

    private static final String DATA_NAME = "adventuremod_calendar";

    private final CalendarDate currentDate;
    private Season lastSeason;
    private int lastDay;
    private long lastWorldTime;

    // Listeners para eventos do calendário
    private static final List<CalendarEventListener> listeners = new ArrayList<>();

    public CalendarManager() {
        this.currentDate = new CalendarDate();
        this.lastSeason = currentDate.getSeason();
        this.lastDay = currentDate.getDayOfYear();
        this.lastWorldTime = 0;
    }

    public static CalendarManager createFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        CalendarManager manager = new CalendarManager();
        if (nbt.contains("calendar")) {
            CalendarDate date = CalendarDate.fromNbt(nbt.getCompound("calendar"));
            manager.currentDate.setYear(date.getYear());
            manager.currentDate.setDayOfYear(date.getDayOfYear());
        }
        manager.lastSeason = manager.currentDate.getSeason();
        manager.lastDay = manager.currentDate.getDayOfYear();
        manager.lastWorldTime = nbt.getLong("lastWorldTime");
        return manager;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.put("calendar", currentDate.toNbt());
        nbt.putLong("lastWorldTime", lastWorldTime);
        return nbt;
    }

    /**
     * Atualiza o calendário baseado no tempo do mundo.
     */
    public void tick(MinecraftServer server) {
        World world = server.getWorld(World.OVERWORLD);
        if (world == null) return;

        long worldTime = world.getTimeOfDay();

        // Atualiza a data atual
        currentDate.updateFromWorldTime(worldTime);

        // Verifica mudança de dia
        if (currentDate.getDayOfYear() != lastDay) {
            onDayChanged(server);
            lastDay = currentDate.getDayOfYear();
        }

        // Verifica mudança de estação
        Season currentSeason = currentDate.getSeason();
        if (currentSeason != lastSeason) {
            onSeasonChanged(server, lastSeason, currentSeason);
            lastSeason = currentSeason;
        }

        lastWorldTime = worldTime;
        markDirty();
    }

    /**
     * Chamado quando o dia muda.
     */
    private void onDayChanged(MinecraftServer server) {
        AdventureMod.LOGGER.info("[Calendar] Novo dia: {}", currentDate.getFormattedDate());

        for (CalendarEventListener listener : listeners) {
            try {
                listener.onDayChanged(this, server);
            } catch (Exception e) {
                AdventureMod.LOGGER.error("Erro no listener de mudança de dia", e);
            }
        }
    }

    /**
     * Chamado quando a estação muda.
     */
    private void onSeasonChanged(MinecraftServer server, Season oldSeason, Season newSeason) {
        AdventureMod.LOGGER.info("[Calendar] Nova estação: {} -> {}",
            oldSeason.getDisplayName(), newSeason.getDisplayName());

        for (CalendarEventListener listener : listeners) {
            try {
                listener.onSeasonChanged(this, server, oldSeason, newSeason);
            } catch (Exception e) {
                AdventureMod.LOGGER.error("Erro no listener de mudança de estação", e);
            }
        }
    }

    /**
     * Registra um listener para eventos do calendário.
     */
    public static void registerListener(CalendarEventListener listener) {
        listeners.add(listener);
    }

    /**
     * Remove um listener.
     */
    public static void unregisterListener(CalendarEventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Obtém a data atual do calendário.
     */
    public CalendarDate getCurrentDate() {
        return currentDate;
    }

    /**
     * Obtém a estação atual.
     */
    public Season getCurrentSeason() {
        return currentDate.getSeason();
    }

    /**
     * Obtém o gerenciador de calendário do servidor.
     */
    public static CalendarManager get(MinecraftServer server) {
        PersistentStateManager stateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        return stateManager.getOrCreate(
            new Type<>(CalendarManager::new, CalendarManager::createFromNbt, null),
            DATA_NAME
        );
    }

    /**
     * Interface para listeners de eventos do calendário.
     */
    public interface CalendarEventListener {
        default void onDayChanged(CalendarManager calendar, MinecraftServer server) {}
        default void onSeasonChanged(CalendarManager calendar, MinecraftServer server,
                                    Season oldSeason, Season newSeason) {}
    }
}

