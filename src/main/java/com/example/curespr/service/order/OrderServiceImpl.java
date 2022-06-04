package com.example.curespr.service.order;

import com.example.curespr.dao.OrderRepository;
import com.example.curespr.entity.Dish;
import com.example.curespr.entity.Order;
import com.example.curespr.entity.User;
import com.example.curespr.service.dish.DishService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService{
    OrderRepository orderRepository;
    DishService dishService;
    public OrderServiceImpl(OrderRepository orderRepository, DishService dishService) {
        this.orderRepository = orderRepository;
        this.dishService = dishService;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAllByUser(User user) {
        return orderRepository.findAllByUser(user);
    }

    @Override
    public Order createOrder(User user, String dishes, Integer total) {
        return orderRepository.save(new Order(user, dishes, total));
    }

    @Override
    public Map<Order, Map<Dish, Integer>> findAllByUserAndResolveAndSortById(User user) {
        Map<Order, Map<Dish, Integer>> orders = new HashMap<>();
        List<Order> orderList = findAllByUser(user);
        orderList.sort(new OrderIdComparator());
        orderList.forEach(order -> orders.put(order, dishService.toMap(order.getDishes())));
        return orders;
    }

    @Override
    public Map<Order, Map<Dish, Integer>> findAllAndResolveAndSortById() {
        Map<Order, Map<Dish, Integer>> orders = new HashMap<>();
        List<Order> orderList = findAll();
        orderList.sort(new OrderIdComparator());
        orderList.forEach(order -> orders.put(order, dishService.toMap(order.getDishes())));
        return orders;
    }

    @Override
    public void remove(Integer id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void setStatus(Integer id, String status) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()){
            Order order = orderOptional.get();
            order.setStatus(status);
            orderRepository.save(order);
        }
    }
}
