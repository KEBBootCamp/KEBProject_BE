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
import java.util.List;

@Controller
public class InspectionController {

  private final InspectionService inspectionService;


  @Autowired
  public InspectionController(InspectionService inspectionService, UserService userService, ExpertService expertService ) {
    this.inspectionService = inspectionService;
  }

  //TC-4 검수 조건 검색
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

  //TC -4 검수 조건 검색 전달
  @PostMapping("/insepctionInfo")
  public String submitInspectionForm(@RequestParam("customerId") String customerId,
                                     @RequestParam("model") String model,
                                     @RequestParam("place") String place,
                                     @RequestParam("inspectDateTime") String inspectDateTime,
                                     Model inspectionModel) {
    if (inspectDateTime == null || inspectDateTime.isEmpty()) {
      return "expertNotFound"; // 검수 날짜가 입력되지 않았을 경우 처리
    }

    LocalDate localDate = LocalDate.parse(inspectDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    Timestamp timestamp = Timestamp.valueOf(localDate.atStartOfDay());

    inspectionModel.addAttribute("customerId", customerId);
    inspectionModel.addAttribute("model", model);
    inspectionModel.addAttribute("place", place);
    inspectionModel.addAttribute("inspectDate", inspectDateTime);

    return "show";
  }


}