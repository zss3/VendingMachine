package com.techelevator.vendingmachine;

public enum ItemTypes {

    CHIP("Crunch Crunch, Yum!"), CANDY("Munch Munch, Yum!"),
    DRINK("Glug Glug, Yum!"), GUM("Chew Chew, Yum!");
    private String sound;

    ItemTypes(String sound) {
        this.sound = sound;
    }

    @Override
    public String toString() {
        return sound;
    }
}
