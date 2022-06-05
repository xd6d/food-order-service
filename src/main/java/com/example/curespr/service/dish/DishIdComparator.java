package com.example.curespr.service.dish;

import com.example.curespr.entity.Dish;
import java.util.Comparator;

public class DishIdComparator implements Comparator<Dish> {
    @Override
    public int compare(Dish o1, Dish o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
