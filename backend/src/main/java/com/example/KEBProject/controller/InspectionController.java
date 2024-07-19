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

  // 검수 신청 부분
  @PostMapping("/matching/request")
  @ResponseBody
  public Map<String, String> submitInspection(@RequestBody Map<String, String> request) {
    String customerId = request.get("customerId");
    String model = request.get("model");
    String place = request.get("place");
    String inspectDate = request.get("inspectDate");

    LocalDate localDate = LocalDate.parse(inspectDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    Timestamp timestamp = Timestamp.valueOf(localDate.atStartOfDay());

    Inspection inspection = inspectionService.createInspection(customerId, model, place, timestamp);
    int matchingId = inspection.getMatchingId();


    return Map.of("redirectUrl", "/inspection_submit/" + matchingId);
  }

//  @PostMapping("/matching/request")
//  @ResponseBody
//  public Map<String, String> submitInspection(@RequestBody Map<String, String> request) {
//    String customerId = request.get("customerId");
//    String model = request.get("model");
//    String place = request.get("place");
//    String inspectDate = request.get("inspectDate");
//
//    LocalDate localDate = LocalDate.parse(inspectDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//    Timestamp timestamp = Timestamp.valueOf(localDate.atStartOfDay());
//
//    String redirectUrl = String.format("/inspection_submit?customerId=%s&model=%s&place=%s&inspectDate=%s",
//        customerId, model, place, inspectDate);
//
//    return Map.of("redirectUrl", redirectUrl);
//  }

  // inspection_submit 페이지
  @GetMapping("/inspection_submit/{matchingId}")
  @ResponseBody
  public Map<String, String> showInspectionSubmitPage(@PathVariable int matchingId) {
    Inspection inspection = inspectionService.getInspectionById(matchingId);

    Map<String, String> response = new HashMap<>();
    response.put("userId", inspection.getCustomerId());
    response.put("model", inspection.getModel());
    response.put("place", inspection.getPlace());
    response.put("inspectDate", inspection.getInspectDate().toLocalDateTime().toLocalDate().toString());

    return response;
  }

  private User getCurrentUser(String userId) {
    return userService.getUserById(userId);
  }

}