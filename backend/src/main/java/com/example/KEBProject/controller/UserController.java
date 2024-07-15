package com.example.KEBProject.controller;

import com.example.KEBProject.entity.User;
import com.example.KEBProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/joinform")
    public String join(Model model) {
        model.addAttribute("user", new User());
        return "join"; // join.html 템플릿을 반환
    }

    @PostMapping("/join")
    public String join(User user) {
        // Checkbox 처리
        if (user.getIsExpert() == null) {
            user.setIsExpert(false);
        }
        userService.createUser(user);
        return "redirect:/users/joinform";
    }
}