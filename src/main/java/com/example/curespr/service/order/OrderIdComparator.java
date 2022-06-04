package com.example.curespr.service.order;

import com.example.curespr.entity.Order;

import java.util.Comparator;

public class OrderIdComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
