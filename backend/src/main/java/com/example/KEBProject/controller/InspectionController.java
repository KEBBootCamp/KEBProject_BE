package com.example.KEBProject.controller;

import com.example.KEBProject.dto.InspectionDTO;
import com.example.KEBProject.entity.Inspection;
import com.example.KEBProject.entity.User;
import com.example.KEBProject.service.ExpertService;
import com.example.KEBProject.service.InspectionService;
import com.example.KEBProject.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/matching")
public class InspectionController {

  @Autowired
  private InspectionService inspectionService;

  @Autowired
  private UserService userService;

  @Autowired
  private ExpertService expertService;

  @GetMapping("/list")
  public ResponseEntity<Map<String, Object>> listInspections(HttpSession session) {
    Map<String, Object> response = new HashMap<>();
    User currentUser = (User) session.getAttribute("user");

    if (currentUser == null) {
      response.put("message", "로그인 후 이용 가능한 기능입니다.");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    if (!currentUser.getIsExpert()) {
      response.put("message", "전문가만 이용 가능한 기능입니다.");
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    List<Inspection> inspections = inspectionService.getInspectionsForExpert(currentUser.getUserId());

    List<Map<String, Object>> inspectionsResponse = new ArrayList<>();
    for (Inspection inspection : inspections) {
      if (inspection.getChecked() == null || inspection.getChecked()) {
        Map<String, Object> inspectionData = new HashMap<>();
        inspectionData.put("matchingId", inspection.getMatchingId());
        inspectionData.put("brand", inspection.getBrand());
        inspectionData.put("model", inspection.getModel());
        inspectionData.put("place", inspection.getPlace());
        inspectionData.put("inspectDate", inspection.getInspectDate());
        inspectionData.put("checked", inspection.getChecked());
        inspectionData.put("complete", inspection.getComplete());

        inspectionsResponse.add(inspectionData);
      }
    }

    response.put("inspections", inspectionsResponse);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/accept/{matchingId}")
  public ResponseEntity<Map<String, String>> checkInspection(@PathVariable int matchingId) {
    Inspection inspection = inspectionService.getInspectionById(matchingId);
    if (inspection != null) {
      inspectionService.updateInspectionChecked(matchingId, true);
      return ResponseEntity.ok(Map.of("status", "accept"));
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "failed"));
  }

  @PostMapping("/reject/{matchingId}")
  public ResponseEntity<Map<String, String>> rejectInspection(@PathVariable int matchingId) {
    Inspection inspection = inspectionService.getInspectionById(matchingId);
    if (inspection != null) {
      inspectionService.updateInspectionChecked(matchingId, false);
      return ResponseEntity.ok(Map.of("status", "rejected"));
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "failed"));
  }

  @PostMapping("/complete/{matchingId}")
  public ResponseEntity<Map<String, String>> completeInspection(@PathVariable int matchingId) {
    Inspection inspection = inspectionService.getInspectionById(matchingId);
    if (inspection != null) {
      inspectionService.updateInspectionComplete(matchingId, true);
      return ResponseEntity.ok(Map.of("status", "complete"));
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "failed"));
  }
}
