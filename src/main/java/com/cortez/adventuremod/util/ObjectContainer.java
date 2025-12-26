package com.cortez.adventuremod.util;

public class ObjectContainer<T>
{
    private T value;

    public ObjectContainer(){}

    public ObjectContainer(T value){
        this.value = value;
    }

    public T getValue(){
        return this.value;
    }
}
