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
      Map<String, Object> inspectionData = new HashMap<>();
      if (inspection.getChecked() == null) {
        inspectionData.put("checked", inspection.getChecked());
        inspectionData.put("matchingId", inspection.getMatchingId());
        inspectionData.put("brand", inspection.getBrand());
        inspectionData.put("model", inspection.getModel());
        inspectionData.put("place", inspection.getPlace());
        inspectionData.put("inspectDate", inspection.getInspectDate());
      } else if (inspection.getChecked() == true) {
        inspectionData.put("complete", inspection.getComplete());
        inspectionData.put("matchingId", inspection.getMatchingId());
        inspectionData.put("brand", inspection.getBrand());
        inspectionData.put("model", inspection.getModel());
        inspectionData.put("place", inspection.getPlace());
        inspectionData.put("inspectDate", inspection.getInspectDate());
      }
      else if(inspection.getChecked() == false) continue;
      inspectionsResponse.add(inspectionData);
    }

    response.put("inspections", inspectionsResponse);
    return ResponseEntity.ok(response);
  }


  // /accept URL 매핑시
  @PostMapping("/accept")
  public ResponseEntity<Map<String, String>> checkInspection(@RequestBody Inspection request) {

    int matchingId = request.getMatchingId();
    Inspection inspection = inspectionService.getInspectionById(matchingId);
    if (inspection != null) {
      if(inspection.getChecked()!=null) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("status", "error"));
      }
      inspectionService.updateInspectionChecked(matchingId, true);
      return ResponseEntity.ok(Map.of("status", "accept"));
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "failed"));
  }

  @PostMapping("/reject")
  public ResponseEntity<Map<String, String>> rejectInspection(@RequestBody Inspection request) {
    int matchingId = request.getMatchingId();
    Inspection inspection = inspectionService.getInspectionById(matchingId);
    if (inspection != null) {
      if(inspection.getChecked()!=null) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("status", "error"));
      }
      inspectionService.updateInspectionChecked(matchingId, false);
      return ResponseEntity.ok(Map.of("status", "rejected"));
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "failed"));
  }

  @PostMapping("/complete")
  public ResponseEntity<Map<String, String>> completeInspection(@RequestBody Inspection request) {
    int matchingId = request.getMatchingId();
    Inspection inspection = inspectionService.getInspectionById(matchingId);
    if (inspection != null) {
      if(inspection.getChecked() != true || inspection.getComplete() != false) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("status", "error"));
      }
      inspectionService.updateInspectionComplete(matchingId, true);
      return ResponseEntity.ok(Map.of("status", "complete"));
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "failed"));
  }
}
