package com.example.KEBProject.controller;

import com.example.KEBProject.entity.Inspection;
import com.example.KEBProject.entity.User;
import com.example.KEBProject.service.ExpertService;
import com.example.KEBProject.service.InspectionService;
import com.example.KEBProject.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class InspectionController {

  private final InspectionService inspectionService;
  private final UserService userService;


  @Autowired
  public InspectionController(InspectionService inspectionService, UserService userService, ExpertService expertService ) {
    this.inspectionService = inspectionService;
    this.userService =  userService;
  }


  @GetMapping("/matching")
  public String showInspectionForm(Model model, HttpSession session) {
    //세션에서 가져옴
    User currentUser = (User) session.getAttribute("user");
    //기존 url 방식
//    User currentUser = getCurrentUser(userId);

    if (currentUser == null) {
      // 유저가 null일 경우에 대한 처리
      return "/login";
    }
    model.addAttribute("userId", currentUser.getUserId());

    //엔지니어 검수 수락 리스트
    if (currentUser.getIsExpert()) {
      List<Inspection> inspections = inspectionService.getInspectionsForExpert(currentUser.getUserId());
      model.addAttribute("inspections", inspections);
      return "engineer_requested";
    }
    //엔지니어 프로필 리스트
    else {
      List<Inspection> inspections = inspectionService.getInspectionsForCustomer(currentUser.getUserId());
      model.addAttribute("inspections", inspections);
      return "inspection_form";
    }
  }


  // inspection_submit 페이지
//  @GetMapping("/inspection_submit/{matchingId}")
//  @ResponseBody
//  public Map<String, String> showInspectionSubmitPage(@PathVariable int matchingId) {
//    Inspection inspection = inspectionService.getInspectionById(matchingId);
//
//    Map<String, String> response = new HashMap<>();
//    response.put("userId", inspection.getCustomerId());
//    response.put("model", inspection.getModel());
//    response.put("place", inspection.getPlace());
//    response.put("inspectDate", inspection.getInspectDate().toLocalDateTime().toLocalDate().toString());
//
//    return response;
//  }

//  private User getCurrentUser(String userId) {
//    return userService.getUserById(userId);
//  }
}