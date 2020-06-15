package com.xintong.code.dm.factory.simple;

public class Run {
    public static void main(String[] args) {
        Animal animalName = Zoo.getAnimalName(Zoo.DOG);
        System.out.println(animalName.getAnimalName());
    }
}
