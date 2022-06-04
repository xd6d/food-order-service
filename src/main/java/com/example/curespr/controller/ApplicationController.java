package com.example.curespr.controller;

import com.example.curespr.service.dish.DishService;
import com.example.curespr.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes(names = {"bucket"})
public class ApplicationController {
    private final DishService dishService;
    private final UserService userService;

    public ApplicationController(DishService dishService, UserService userService) {
        this.dishService = dishService;
        this.userService = userService;
    }


    @GetMapping(value = {"/menu", ""})
    public String index(Model model) {
        model.addAttribute("dishes", dishService.findAllAndSortById());
        if (!model.containsAttribute("bucket"))
            model.addAttribute("bucket", "");
        model.addAttribute("bucketMap", dishService.toMap((String) model.getAttribute("bucket")));
        return "index";
    }

    @GetMapping("/create-account")
    public String createAccountGet() {
        return "guest/create-account";
    }

    @PostMapping("/create-account")
    public String createAccountPost(@RequestParam(name = "username", required = false) String username,
                                    @RequestParam(name = "password", required = false) String password, Model model) {
        if (username == null || password == null) {
            model.addAttribute("exception", "Your username or password was entered incorrectly.");
            return "guest/create-account";
        }
        if (userService.createUser(username, password) == null) {
            model.addAttribute("exception", "User with the given username already exists.");
            model.addAttribute("username", username);
            return "guest/create-account";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginGet() {
        return "guest/login";
    }
}
