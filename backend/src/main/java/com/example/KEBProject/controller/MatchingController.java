package com.example.KEBProject.controller;

import com.example.KEBProject.dto.InspectionDTO;
import com.example.KEBProject.entity.Inspection;
import com.example.KEBProject.entity.User;
import com.example.KEBProject.service.InspectionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class MatchingController {

    private final InspectionService inspectionService;
    @Autowired
    public MatchingController(InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

    //TC -4 검수 조건 검색 전달
    @GetMapping("/matching/inspectionInfo")
    public String submitInspectionForm(@RequestParam("customerId") String customerId,
                                       @RequestParam("brand") String brand,
                                       @RequestParam("model") String model,
                                       @RequestParam("place") String place,
                                       @RequestParam("inspectDateTime") String inspectDateTime,
                                       HttpSession session) {

        if (customerId == null || customerId.isEmpty() ||
                model == null || model.isEmpty() ||
                brand == null || brand.isEmpty() ||
                place == null || place.isEmpty()) {
            return "expertNotFound"; // 필수 매개변수가 없을 경우 처리
        }


        User user = (User) session.getAttribute("user");
        InspectionDTO inspectionDTO = new InspectionDTO();
        inspectionDTO.setCustomerId(user.getUserId());
        inspectionDTO.setModel(model);
        inspectionDTO.setBrand(brand);
        inspectionDTO.setPlace(place);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(inspectDateTime, formatter);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        inspectionDTO.setInspectDate(timestamp);

        // 성공 메시지를 반환하거나 적절한 처리를 수행
        return "redirect:/expert/list";
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
