package com.example.KEBProject.controller;

import com.example.KEBProject.entity.Inspection;
import com.example.KEBProject.entity.User;
import com.example.KEBProject.service.ExpertService;
import com.example.KEBProject.service.InspectionService;
import com.example.KEBProject.service.UserService;
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
  private final ExpertService expertService;

  @Autowired
  public InspectionController(InspectionService inspectionService, UserService userService, ExpertService expertService ) {
    this.inspectionService = inspectionService;
    this.userService =  userService;
    this.expertService =  expertService;
  }


  //처음 화면

  @GetMapping("/matching/{userId}")
  public String showInspectionForm(@PathVariable String userId, Model model) {
    User currentUser = getCurrentUser(userId);

    if (currentUser == null) {
      // 유저가 null일 경우에 대한 처리 (예: 로그인 페이지로 리다이렉트)
      return "redirect:/login";
    }

    model.addAttribute("userId", currentUser.getUserId());

    if (currentUser.getIsExpert()) {
      List<Inspection> inspections = inspectionService.getInspectionsForExpert(currentUser.getUserId());
      model.addAttribute("inspections", inspections);
      return "engineer_requested";
    }
    else {
      List<Inspection> inspections = inspectionService.getInspectionsForCustomer(currentUser.getUserId());
      model.addAttribute("inspections", inspections);
      return "inspection_form";
    }
  }


  // 엔지니어 검수 요청 목록
  @GetMapping("/matching/request/list")
  @ResponseBody
  public String showEngineerRequested(@PathVariable String userId, Model model) {
    List<Inspection> inspections = inspectionService.getInspectionsForExpert(userId);
    model.addAttribute("inspections", inspections);
    return "engineer_requested";
  }

  // 엔지니어 검수 요청 상세보기
  @GetMapping("/inspection_detail/{matchingId}")
  public String checkInspection(@RequestParam int matchingId, Model model) {
    Inspection inspection = inspectionService.getInspectionById(matchingId);

    model.addAttribute("inspection", inspection);

    return "inspection_detail";
  }

  // 엔지니어 검수 수락 여부
  @PostMapping("/matching/request/accpet/{matchingId}")
  @ResponseBody
  public Map<String, String> checkInspection(@PathVariable int matchingId, @RequestBody Map<String,Boolean> request) {
    boolean checked = request.get("checked");

    Inspection inspection = inspectionService.getInspectionById(matchingId);
    if (inspection != null){
      inspectionService.updateInspectionChecked(matchingId, checked);
    }

    return Map.of("status","succeess");
  }


  private User getCurrentUser(String userId) {
    return userService.getUserById(userId);
  }

}