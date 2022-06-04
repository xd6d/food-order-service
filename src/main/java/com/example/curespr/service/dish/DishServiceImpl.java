package com.example.curespr.service.dish;

import com.example.curespr.dao.DishRepository;
import com.example.curespr.entity.Dish;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    @Override
    public List<Dish> findAllAndSortById() {
        List<Dish> list = dishRepository.findAll();
        list.sort(new DishIdComparator());
        return list;
    }
    @Override
    public int getTotal(String bucket) {
        Map<Dish, Integer> order = toMap(bucket);
        int s = 0;
        for (Dish dish : order.keySet()) {
            s += dish.getPrice() * order.get(dish);
        }
        return s;
    }

    @Override
    public String addDish(Integer id, String bucket) {
        int amount;
        int start;
        int end;
        if (bucket.contains("." + id)) {
            start = bucket.indexOf("." + id) + 2 + countNumbers(id);
            end = bucket.indexOf(".", start);
            if (end == -1)
                end = bucket.length();
            amount = Integer.parseInt(bucket.substring(start, end));
            return bucket.replaceFirst("[.]" + id + "[:]" + amount, "." + id + ":" + (amount + 1));
        }
        return bucket.concat("." + id + ":" + 1);
    }

    private int countNumbers(Integer id){
        int count = 1;
        while (id > 9) {
            count++;
            id /= 10;
        }
        return count;
    }


    @Override
    public String minusDish(Integer id, String bucket) {
        int amount;
        int start;
        int end;
        if (bucket.contains("." + id)) {
            start = bucket.indexOf("." + id) + 2 + countNumbers(id);
            end = bucket.indexOf(".", start);
            if (end == -1)
                end = bucket.length();
            amount = Integer.parseInt(bucket.substring(start, end));
            if (amount == 1)
                return bucket.replaceFirst("[.]" + id + "[:]" + amount, "");
            return bucket.replaceFirst("[.]" + id + "[:]" + amount, "." + id + ":" + (amount - 1));
        }
        return bucket;
    }

    @Override
    public Map<Dish, Integer> toMap(String bucket) {
        Map<Dish, Integer> order = new HashMap<>();
        int start = bucket.indexOf('.');
        while (start!=-1){
            int end = bucket.indexOf(':',start);
            int id = Integer.parseInt(bucket.substring(start+1, end));
            start = end;
            end = bucket.indexOf('.', start+1);
            if (end==-1) end = bucket.length();
            int amount = Integer.parseInt(bucket.substring(start+1, end));
            order.put(dishRepository.getById(id), amount);
            start = bucket.indexOf('.', start+1);
        }
        return order;
    }

    @Override
    public Dish createDish(String name, String img, String description, int price) {
        return dishRepository.save(new Dish(name, img, description, price));
    }

    @Override
    public void removeDish(Integer id) {
        dishRepository.deleteById(id);
    }

}
