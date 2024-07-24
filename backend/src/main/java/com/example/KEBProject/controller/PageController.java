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
}