package com.xintong.code.dm.factory.simple;

public class Zoo {
    public static final String CAT="cat";
    public static final String DOG="dog";
    public static final String PIG="pig";
    public static Animal getAnimalName(String name){
        if (name.equals(CAT)) return new Cat();
        if (name.equals(DOG)) return new Dog();
        if (name.equals(PIG)) return new Pig();
        return null;

    }
}
