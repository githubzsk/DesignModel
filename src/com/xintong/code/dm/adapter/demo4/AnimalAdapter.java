package com.xintong.code.dm.adapter.demo4;

public class AnimalAdapter implements Animal{

    private Person person;

    public AnimalAdapter(Person person){
        this.person = person;
    }
    @Override
    public void speak() {
        person.speak();
    }
}
