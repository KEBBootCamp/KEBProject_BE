package com.example.KEBProject.controller;

import com.example.KEBProject.dto.ExpertDTO;
import com.example.KEBProject.dto.InspectionDTO;
import com.example.KEBProject.entity.Inspection;
import com.example.KEBProject.entity.User;
import com.example.KEBProject.service.ExpertListService;
import com.example.KEBProject.service.InspectionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MatchingController {

    @Autowired
    private ExpertListService expertListService;

    //TC -4 검수 조건 검색 전달
    @GetMapping("/matching/inspectionInfo")
    public ResponseEntity<Map<String, Object>> submitInspectionForm(@RequestParam("customerId") String customerId,
                                                                    @RequestParam("brand") String brand,
                                                                    @RequestParam("model") String model,
                                                                    @RequestParam("place") String place,
                                                                    @RequestParam("inspectDateTime") String inspectDateTime,
                                                                    HttpSession session,
                                                                    RedirectAttributes redirectAttributes) {
        Map<String, Object> response = new HashMap<>();
        if (customerId == null || customerId.isEmpty() ||
                model == null || model.isEmpty() ||
                brand == null || brand.isEmpty() ||
                place == null || place.isEmpty()) {
            response.put("message", "Param Null or Empty");
            //status 400
            return ResponseEntity.badRequest().body(response);
        }

        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.put("message", "User is not an expert or not logged in.");
            // status 401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        InspectionDTO inspectionDTO = new InspectionDTO();
        inspectionDTO.setCustomerId(user.getUserId());
        inspectionDTO.setModel(model);
        inspectionDTO.setBrand(brand);
        inspectionDTO.setPlace(place);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(inspectDateTime, formatter);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        inspectionDTO.setInspectDate(timestamp);

        response.put("message", "Success");
        response.put("inspectionDTO", inspectionDTO);

        List<ExpertDTO> expertDto = expertListService.showExpertsDto(inspectionDTO.getBrand());
        response.put("expertDto", expertDto);


        return ResponseEntity.ok(response);
    }


    //TC-4 검수 조건 검색
//    @GetMapping("/matching")
//    public String showInspectionForm(Model model, HttpSession session) {
//        //세션에서 가져옴
//        User currentUser = (User) session.getAttribute("user");
//
//        if (currentUser == null) {
//            // 유저가 null일 경우에 대한 처리 (예: 로그인 페이지로 리다이렉트)
//            return "/login";
//        }
//
//        model.addAttribute("userId", currentUser.getUserId());
//
//        //엔지니어 검수 수락 리스트
//        if (currentUser.getIsExpert()) {
//            List<Inspection> inspections = inspectionService.getInspectionsForExpert(currentUser.getUserId());
//
//            //검수 요청 응답한 것은 목록에서 사라지도록 설정
//            List<Inspection> acceptInspections = inspections.stream()
//                    .filter(inspection -> inspection.getChecked() == null)
//                    .toList();
//            model.addAttribute("inspections", acceptInspections);
//
//            return "engineer_requested";
//
//        }
//        //엔지니어 프로필 리스트
//        else {
//            List<Inspection> inspections = inspectionService.getInspectionsForCustomer(currentUser.getUserId());
//            model.addAttribute("inspections", inspections);
//            return "inspection_form";
//        }
//    }



}
