package com.example.KEBProject.controller;


import com.example.KEBProject.entity.Expert;
import com.example.KEBProject.entity.User;
import com.example.KEBProject.service.ExpertService;
import com.example.KEBProject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/pages")
public class PageController {

    private final UserService userService;
    private final ExpertService expertService;

    public PageController(UserService userService, ExpertService expertService) {
        this.userService = userService;
        this.expertService = expertService;
    }

    @GetMapping("/joinform")
    public String joinForm(Model model) {
        model.addAttribute("user", new User());
        return "join"; // join.html 템플릿을 반환
    }

    @GetMapping("/loginform")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "login"; // login.html 템플릿을 반환
    }

    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }

}