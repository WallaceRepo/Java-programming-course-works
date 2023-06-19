package dev.lpa;

import com.abc.first.Item;

public class Main {
    public static void main(String[] args) {
     //    com.abc.first.Item firstItem = new com.abc.first.Item("Burger");
       Item firstItem = new Item("burger");
        System.out.println(firstItem);
    }
}

package com.abc.first;

public class Item {
    private String type;

    public Item(String type){
        this.type = type;
    }

    @Override
    public String toString() {
        return "Item{" +
                "type='" +type + '\'' +
                '}';
    }
}

