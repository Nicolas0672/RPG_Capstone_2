package com.pluralsight.services;

import com.pluralsight.model.Item;
import com.pluralsight.model.Weapon;

import java.util.List;

public class OrderService {
    private List<Item> totalList;

    public OrderService(List<Item> totalList) {
        this.totalList = totalList;
    }

    public List<Weapon> addWeapon() {

    }
}
