package com.example.KEBProject.controller;

import com.example.KEBProject.dto.ExpertDTO;
import com.example.KEBProject.dto.UserDTO;
import com.example.KEBProject.entity.Expert;
import com.example.KEBProject.entity.Inspection;
import com.example.KEBProject.entity.User;
import com.example.KEBProject.service.ExpertService;
import com.example.KEBProject.service.InspectionService;
import com.example.KEBProject.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

  @Autowired
  private ExpertService expertService;

  @Autowired
  private UserService userService;

  @Autowired
  private InspectionService inspectionService;

  @GetMapping("/customer")
  @ResponseBody
  public ResponseEntity<Map<String, Object>> customerMypage(HttpSession session) {
    Map<String, Object> response = new HashMap<>();
    User user = (User) session.getAttribute("user");
    if (user != null) {
      response.put("user", user);

      // 고객이 신청한 검수 내역
      List<Inspection> customerInspection = inspectionService.getInspectionsForCustomer(user.getUserId());

      List<Map<String, Object>> inspectionsResponse = new ArrayList<>();
      for (Inspection inspection : customerInspection) {
        Map<String, Object> inspectionData = new HashMap<>();
        inspectionData.put("matchingId", inspection.getMatchingId());
        inspectionData.put("brand", inspection.getBrand());
        inspectionData.put("model", inspection.getModel());
        inspectionData.put("place", inspection.getPlace());
        inspectionData.put("inspectDate", inspection.getInspectDate());
        inspectionData.put("checked", inspection.getChecked());
        inspectionData.put("complete", inspection.getComplete());

        User engineer = inspection.getEngineer().getUser();

        if (engineer != null) {
          Map<String, Object> engineerData = new HashMap<>();
          engineerData.put("userId", engineer.getUserId());
          engineerData.put("userName", engineer.getUserName());
          engineerData.put("userPhonenumber", engineer.getUserPhonenumber());
          inspectionData.put("expert", engineerData);
        } else {
          inspectionData.put("expert", null);
        }

        inspectionsResponse.add(inspectionData);
      }

      response.put("inspections", inspectionsResponse);
    } else {
      response.put("user", null);
    }
    return ResponseEntity.ok(response);
  }

  @GetMapping("/engineer")
  @ResponseBody
  public ResponseEntity<Map<String, Object>> engineerMypage(HttpSession session) {
    Map<String, Object> response = new HashMap<>();
    User user = (User) session.getAttribute("user");

    if (user != null && user.getIsExpert()) {
      response.put("user", user);
    }
    return ResponseEntity.ok(response);
  }

  //전문가 개인정보 수정
  @GetMapping("/edit")
  @ResponseBody
  public ResponseEntity<Map<String, Object>> editProfile(HttpSession session) {
    Map<String, Object> response = new HashMap<>();
    User user = (User) session.getAttribute("user");

    if (user != null && user.getIsExpert()) {
      Expert expert = expertService.getExpertById(user.getUserId());
      response.put("expert", expert);
      return ResponseEntity.ok(response);
    }
    response.put("message", "User is not an expert or not logged in.");
    return ResponseEntity.badRequest().body(response);
  }

  @PutMapping("/update")
  @ResponseBody
  public ResponseEntity<Map<String, Object>> updateProfile(
      @RequestParam Integer engineerCareer,
      @RequestParam String engineerBrand,
      @RequestParam String engineerProfile,
      HttpSession session) {

    Map<String, Object> response = new HashMap<>();
    User currentUser = (User) session.getAttribute("user");

    if (currentUser != null && currentUser.getIsExpert()) {

      Expert expert = expertService.getExpertById(currentUser.getUserId());

      expert.setEngineerCareer(engineerCareer);
      expert.setEngineerBrand(engineerBrand);
      expert.setEngineerProfile(engineerProfile);

      expertService.updateExpert(expert);
      session.setAttribute("user", currentUser);

      response.put("message", "Profile updated successfully.");
      return ResponseEntity.ok(response);
    }

    response.put("message", "User is not an expert or not logged in.");
    return ResponseEntity.badRequest().body(response);
  }


}
