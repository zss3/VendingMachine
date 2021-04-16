package com.techelevator.vendingmachine;

import java.math.BigDecimal;

public class ItemSlot {

    public static int STARTING_ITEM_COUNT = 5;
    private final BigDecimal price;
    private final String name;
    private final ItemTypes type;
    private int count = STARTING_ITEM_COUNT;

    public ItemSlot(BigDecimal price, String name, ItemTypes type) {
        this.price = price;
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ItemTypes getType() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    }


