package com.example.curespr.controller;

import com.example.curespr.service.dish.DishService;
import com.example.curespr.service.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    OrderService orderService;
    DishService dishService;

    public AdminController(DishService dishService, OrderService orderService) {
        this.dishService = dishService;
        this.orderService = orderService;
    }

    @PostMapping("/add-dish")
    public String addDish(@RequestParam(name = "name") String name,
                          @RequestParam(name = "description") String description,
                          @RequestParam(name = "img") String img,
                          @RequestParam(name = "price") Integer price){
        dishService.createDish(name, img, description, price);
        return "redirect:/menu";
    }

    @PostMapping("/remove-dish")
    public String removeDish(@RequestParam(name = "remove") Integer id){
        dishService.removeDish(id);
        return "redirect:/menu";
    }

    @GetMapping("/orders")
    public String orders(Model model){
        model.addAttribute("orders", orderService.findAllAndResolveAndSortById());
        return "admin/admin-orders";
    }

    @PostMapping("/accept")
    public String accept(@RequestParam(name = "id") Integer id){
        orderService.setStatus(id, "Accepted");
        return "redirect:/admin/orders";
    }

    @PostMapping("/deny")
    public String deny(@RequestParam(name = "id") Integer id){
        orderService.setStatus(id, "Denied");
        return "redirect:/admin/orders";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam(name = "id") Integer id){
        orderService.remove(id);
        return "redirect:/admin/orders";
    }
}
