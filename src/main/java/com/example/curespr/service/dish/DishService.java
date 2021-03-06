package com.example.curespr.service.dish;

import com.example.curespr.entity.Dish;
import java.util.List;
import java.util.Map;

public interface DishService {

    List<Dish> findAll();

    List<Dish> findAllAndSortById();

    int getTotal(String order);

    String addDish(Integer id, String bucket);

    String minusDish(Integer id, String bucket);

    Map<Dish, Integer> toMap(String bucket);

    Dish createDish(String name, String img, String description, int price);

    void removeDish(Integer id);
}
