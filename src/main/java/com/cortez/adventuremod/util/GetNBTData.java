package com.cortez.adventuremod.util;

import java.util.Optional;

public class GetNBTData
{
    IEntityDataSaver dataPlayer;

    public GetNBTData(IEntityDataSaver dataPlayer){
        this.dataPlayer = dataPlayer;
    }

    public Optional<String> getString(String key){
        String data = this.dataPlayer.getPersistentData().getString(key);
        if (data == null || data.isBlank()) {
            return Optional.empty();
        }
        return Optional.of(data);
    }

    public Optional<Integer> getInt(String key){
        if (this.dataPlayer.getPersistentData().contains(key)) {
            return Optional.of(this.dataPlayer.getPersistentData().getInt(key));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Float> getFloat(String key){
        if (this.dataPlayer.getPersistentData().contains(key)) {
            return Optional.of(this.dataPlayer.getPersistentData().getFloat(key));
        } else {
            return Optional.empty();
        }
    }
}
