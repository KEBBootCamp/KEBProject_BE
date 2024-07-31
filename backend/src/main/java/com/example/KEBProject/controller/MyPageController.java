package com.example.KEBProject.controller;

import com.example.KEBProject.entity.Expert;
import com.example.KEBProject.entity.User;
import com.example.KEBProject.service.ExpertService;
import com.example.KEBProject.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MyPageController {

  private final UserService userService;
  private final ExpertService expertService;

  public MyPageController(UserService userService, ExpertService expertService) {
    this.userService = userService;
    this.expertService = expertService;
  }

  @GetMapping("/mypage")
  public String myPage(HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    if (user != null) {
      model.addAttribute("user", user);

      if (user.getIsExpert()) {
        Expert expert = expertService.getExpertById(user.getUserId());
        model.addAttribute("expert", expert);
      }

    } else {
      model.addAttribute("user", null);
    }
    return "mypage";
  }

  @GetMapping("/mypage/edit")
  public String editProfile(HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    if (user != null && user.getIsExpert()) {
      Expert expert = expertService.getExpertById(user.getUserId());
      model.addAttribute("expert", expert);
      return "edit_engineerProfile";
    }
    return "redirect:/mypage";
  }

  @PostMapping("/mypage/update")
  public String updateProfile(@ModelAttribute Expert expert, HttpSession session) {
    User currentUser = (User) session.getAttribute("user");
    if (currentUser != null && currentUser.getIsExpert()) {
      expert.setEngineerId(currentUser.getUserId());
      expertService.updateExpert(expert);
      session.setAttribute("user", currentUser);
    }
    return "redirect:/mypage";
  }
}
