package com.example.curespr.controller;

import com.example.curespr.entity.User;
import com.example.curespr.service.dish.DishService;
import com.example.curespr.service.order.OrderService;
import com.example.curespr.service.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping(value = "/user")
@SessionAttributes(names = {"bucket"})
public class UserController {
    DishService dishService;
    OrderService orderService;
    UserService userService;

    public UserController(DishService dishService, OrderService orderService, UserService userService) {
        this.dishService = dishService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/order")
    public String currentOrder(@CookieValue(name = "success", required = false, defaultValue = "") String success,
                               Model model) {
        String bucket = (String) model.getAttribute("bucket");
        model.addAttribute("bucketMap",dishService.toMap(bucket));
        model.addAttribute("total", dishService.getTotal(bucket));
        model.addAttribute("success", success);
        return "user/current-order";
    }

    @PostMapping("/add")
    public String add(@RequestParam(name = "add") Integer id, Model model) {
        String bucket = (String) model.getAttribute("bucket");
        model.addAttribute("bucket", dishService.addDish(id, bucket));
        return "redirect:/menu";
    }

    @PostMapping("/plus")
    public String plus(@RequestParam(name = "add") Integer id, Model model) {
        String bucket = (String) model.getAttribute("bucket");
        model.addAttribute("bucket", dishService.addDish(id, bucket));
        return "redirect:order";
    }
    @PostMapping("/minus")
    public String minus(@RequestParam(name = "minus") Integer id, Model model) {
        String bucket = (String) model.getAttribute("bucket");
        model.addAttribute("bucket", dishService.minusDish(id, bucket));
        return "redirect:order";
    }
    @PostMapping("/confirm")
    public String confirm(HttpServletResponse response, Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        Optional<User> optionalUser = userService.findByUsername(username);
        String bucket = (String) model.getAttribute("bucket");
        if (Objects.equals(bucket, ""))
            return "redirect:/menu";
        if (optionalUser.isPresent()) {
            orderService.createOrder(optionalUser.get(), bucket, dishService.getTotal(bucket));
            model.addAttribute("bucket", "");
            Cookie cookie = new Cookie("success", "success");
            cookie.setMaxAge(1);
            response.addCookie(cookie);
        }
        return "redirect:order";
    }

    @GetMapping("/orders")
    public String myOrders(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        Optional<User> optionalUser = userService.findByUsername(username);
        optionalUser.ifPresent(user ->
                model.addAttribute("myorders", orderService.findAllByUserAndResolveAndSortById(user)));
        return "user/my-orders";
    }
}
