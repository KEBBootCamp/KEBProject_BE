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

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/expert")
public class    SearchlistController {

    @Autowired
    private ExpertListService expertListService;
    @Autowired
    private InspectionService inspectionService;

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> tests(@ModelAttribute("inspectionDTO")InspectionDTO inspectionDTO, Model model) {
        Map<String, Object> response = new HashMap<>();
        response.put("inspectionDTO", inspectionDTO);

        List<ExpertDTO> expertDto = expertListService.showExpertsDto();
        response.put("expertDto", expertDto);

        return ResponseEntity.ok(response);
        //http://localhost:8081/matching/inspectionInfo?customerId=ss&brand=b&model=m&place=p&inspectDateTime=2024-08-05T10%3A30
    }


    @GetMapping("/expertDetails")
    public ResponseEntity<Map<String, Object>> expertInfo(
            @RequestParam("userId") String userId,
            @RequestParam("customerId") String customerId,
            @RequestParam("model") String model,
            @RequestParam("brand") String brand,
            @RequestParam("place") String place,
            @RequestParam("inspectDateTime") Timestamp inspectDateTime,
            HttpSession session) {

        User user = (User) session.getAttribute("user");

        Map<String, Object> response = new HashMap<>();
        response.put("customerId", user.getUserId());
        response.put("expertId", userId);
        response.put("model", model);
        response.put("brand", brand);
        response.put("place", place);
        response.put("inspectDateTime", inspectDateTime);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/createInspection")
    public ResponseEntity<String> createInspection(@RequestBody InspectionDTO request, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Inspection inspection = new Inspection();

        inspection.setCustomerId(user.getUserId());
        inspection.setEngineerId(request.getEngineerId());
        inspection.setModel(request.getModel());
        inspection.setBrand(request.getBrand());
        inspection.setPlace(request.getPlace());
        inspection.setInspectDate(request.getInspectDate());
        inspection.setComplete(false);

        inspectionService.createInspection(inspection);
        return ResponseEntity.status(HttpStatus.CREATED).body("Inspection success!");
    }

    //TC-5 엔지니어 목록
//    @GetMapping("/list")
//    public String tests(@ModelAttribute("inspectionDTO")InspectionDTO inspectionDTO, Model model) {
//        model.addAttribute("inspectionDTO", inspectionDTO);
//
//        List<ExpertDTO> expertDto = expertListService.showExpertsDto();
//        model.addAttribute("expertDto", expertDto);
//
//        return "expertList";
//    }
    //TC-6 엔지니어 상세정보
//    @PostMapping("/expertDetails")
//    public String expertInfo(@RequestParam("userId") String userId,
//                             @RequestParam("customerId") String customerId,
//                             @RequestParam("model") String model,
//                             @RequestParam("brand") String brand,
//                             @RequestParam("place") String place,
//                             @RequestParam("inspectDateTime") Timestamp inspectDateTime,
//                             HttpSession session,  Model inspectionmodel) {
//        User user = (User) session.getAttribute("user");
//
//        inspectionmodel.addAttribute("customerId",user.getUserId());
//        inspectionmodel.addAttribute("expertId",userId  );
//        inspectionmodel.addAttribute("model",model);
//        inspectionmodel.addAttribute("brand",brand);
//        inspectionmodel.addAttribute("place",place);
//
//        inspectionmodel.addAttribute("inspectDateTime",inspectDateTime);
//
//
//
//        return "expertInfo";
//    }

    //    @PostMapping("/createInspection")
//    public String createInspection(@RequestParam("userId") String userId,
//                                   @RequestParam("customerId") String customerId,
//                                   @RequestParam("model") String model,
//                                   @RequestParam("brand") String brand,
//                                   @RequestParam("place") String place,
//                                   @RequestParam("inspectDateTime") Timestamp inspectDateTime,
//                                   HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        Inspection inspection = new Inspection();
//
//        inspection.setCustomerId(user.getUserId());
//        inspection.setEngineerId(userId);
//        inspection.setModel(model);
//        inspection.setBrand(brand);
//        inspection.setPlace(place);
//        inspection.setInspectDate(inspectDateTime);
////        inspection.setChecked(false);   검수 신청 시에는 null
//        inspection.setComplete(false);
//
//        inspectionService.createInspection(inspection);
//        return "redirect:/mypage";
//    }

}

