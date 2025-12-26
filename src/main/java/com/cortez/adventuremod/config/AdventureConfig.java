package com.cortez.adventuremod.config;

import com.cortez.adventuremod.AdventureMod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class AdventureConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = AdventureMod.MODID+".json";
    private static AdventureConfigData data;

    private AdventureConfig() {}

    public static void load(){
        File configFile = FabricLoader.getInstance()
                .getConfigDir()
                .resolve(FILE_NAME)
                .toFile();

        if (!configFile.exists()) {
            data = AdventureConfigData.defaultConfig();
            save(configFile);
            AdventureMod.LOGGER.info("[Adventure Mod] Arquivo de configuração criado: " + configFile.getAbsolutePath());
            return;
        }

        try (FileReader reader = new FileReader(configFile)) {
            data = GSON.fromJson(reader, AdventureConfigData.class);
            if (data == null) {
                data = AdventureConfigData.defaultConfig();
                save(configFile);
                return;
            }

            validateConfig();

            AdventureMod.LOGGER.info("[Adventure Mod] Configuração carregada:");

        }catch (Exception e){
            AdventureMod.LOGGER.error("[Adventure Mod] Erro ao carregar configuração: " + e.getMessage());
            data = AdventureConfigData.defaultConfig();
            save(configFile);
        }
    }

    private static void validateConfig() {

    }

    private static void save(File file) {
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(data, writer);
        } catch (Exception e) {
            AdventureMod.LOGGER.error("[Adventure Mod] Erro ao salvar configuração: " + e.getMessage());
        }
    }

    public static boolean isDebug(){
        return data != null && data.debug;
    }

    public static boolean isThirst(){
        return data != null && data.thirst;
    }

    public static boolean isAltitudeEffect(){
        return data != null && data.altitudeEffect;
    }



}
