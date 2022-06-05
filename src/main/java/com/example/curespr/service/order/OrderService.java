package com.example.curespr.service.order;

import com.example.curespr.entity.Dish;
import com.example.curespr.entity.Order;
import com.example.curespr.entity.User;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {
    List<Order> findAll();

    Optional<Order> findById(Integer id);

    List<Order> findAllByUser(User user);

    Order createOrder(User user, String dishes, Integer total);

    Map<Order, Map<Dish, Integer>> findAllByUserAndResolveAndSortById(User user);

    Map<Order, Map<Dish, Integer>> findAllAndResolveAndSortById();

    void remove(Integer id);

    void setStatus(Integer id, String status);
}
