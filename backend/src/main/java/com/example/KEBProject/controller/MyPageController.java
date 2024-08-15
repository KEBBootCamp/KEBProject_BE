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
    User sessionUser  = (User) session.getAttribute("user");

    if (sessionUser  != null) {

      User user = userService.findById(sessionUser.getUserId());

      Map<String, Object> userData = new HashMap<>();

      userData.put("userName", user.getUserName());
      userData.put("isExpert", user.getIsExpert());

      response.put("user", userData);

      // 고객이 신청한 검수 내역
      List<Inspection> customerInspection = inspectionService.getInspectionsForCustomer(user.getUserId());

      List<Map<String, Object>> inspectionsResponse = new ArrayList<>();
      for (Inspection inspection : customerInspection) {
        Map<String, Object> inspectionData = new HashMap<>();
        inspectionData.put("brand", inspection.getBrand());
        inspectionData.put("model", inspection.getModel());
        inspectionData.put("place", inspection.getPlace());
        inspectionData.put("inspectDate", inspection.getInspectDate());
        inspectionData.put("checked", inspection.getChecked());
        inspectionData.put("complete", inspection.getComplete());

        User engineer = inspection.getEngineer().getUser();

        if (engineer != null) {
          Map<String, Object> engineerData = new HashMap<>();
          engineerData.put("userName", engineer.getUserName());

          inspectionData.put("expert", engineerData);
        } else {
          inspectionData.put("expert", null);
        }

        inspectionsResponse.add(inspectionData);
      }

      response.put("inspections", inspectionsResponse);
    } else {
      response.put("message", "지정된 사용자를 찾을 수 없습니다");
    }
    return ResponseEntity.ok(response);
  }

  @GetMapping("/engineer")
  @ResponseBody
  public ResponseEntity<Map<String, Object>> engineerMypage(HttpSession session) {
    Map<String, Object> response = new HashMap<>();
    User user = (User) session.getAttribute("user");

    if (user != null && user.getIsExpert()) {
      Expert expert = expertService.getExpertById(user.getUserId());

      ExpertDTO expertDTO = new ExpertDTO(expert, user);
      Map<String, Object> expertData = new HashMap<>();

      expertData.put("userName", expertDTO.getUserName());
      expertData.put("isExpert", expertDTO.getIsExpert());

      response.put("expert",expertData);
    }
     else{
       response.put("message", "지정된 사용자를 찾을 수 없습니다.");
    }
    return ResponseEntity.ok(response);
  }

  //전문가 개인정보 수정 페이지
  @GetMapping("/edit")
  @ResponseBody
  public ResponseEntity<Map<String, Object>> editProfile(HttpSession session) {
    Map<String, Object> response = new HashMap<>();
    User user = (User) session.getAttribute("user");

    if (user != null && user.getIsExpert()) {
      Expert expert = expertService.getExpertById(user.getUserId());

      ExpertDTO expertDTO  = new ExpertDTO(expert, user);

      Map<String, Object> expertData = new HashMap<>();
      expertData.put("engineerCareer",expertDTO.getEngineerCareer());
      expertData.put("engineerBrand",expertDTO.getEngineerBrand());
      expertData.put("engineerProfile",expertDTO.getEngineerProfile());
      expertData.put("userPhonenumber",expertDTO.getUserPhonenumber());

      response.put("expert", expertData);
      return ResponseEntity.ok(response);
    }
    response.put("message", "전문가가 아니거나 로그인되지 않았습니다.");
    return ResponseEntity.badRequest().body(response);
  }


  @PutMapping("/update")
  @ResponseBody
  public ResponseEntity<Map<String, Object>> updateProfile(
      @RequestBody ExpertDTO expertDTO,
      HttpSession session) {

    Map<String, Object> response = new HashMap<>();
    User currentUser = (User) session.getAttribute("user");

    if (currentUser != null && currentUser.getIsExpert()) {

      Expert expert = expertService.getExpertById(currentUser.getUserId());

      expert.setEngineerCareer(expertDTO.getEngineerCareer());
      expert.setEngineerBrand(expertDTO.getEngineerBrand());
      expert.setEngineerProfile(expertDTO.getEngineerProfile());
      expert.getUser().setUserPhonenumber(expertDTO.getUserPhonenumber());

      currentUser.setUserPhonenumber(expertDTO.getUserPhonenumber());

      expertService.updateExpert(expert);
      session.setAttribute("user", currentUser);

      response.put("message", "Profile updated successfully.");
      return ResponseEntity.ok(response);
    }

    response.put("message", "User is not an expert or not logged in.");
    return ResponseEntity.badRequest().body(response);
  }

}
