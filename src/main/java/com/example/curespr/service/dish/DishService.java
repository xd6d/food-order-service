package com.example.curespr.service.dish;

import com.example.curespr.entity.Dish;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
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
