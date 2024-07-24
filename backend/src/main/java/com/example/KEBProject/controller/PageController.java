package com.example.KEBProject.controller;


import com.example.KEBProject.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages")
public class PageController {

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
    public String homePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", null);
        }
        return "home"; // home.html 템플릿을 반환
    }
}