package com.example.KEBProject.controller;



import com.example.KEBProject.dto.ExpertDTO;
import com.example.KEBProject.dto.InspectionDTO;
import com.example.KEBProject.service.ExpertListService;
import com.example.KEBProject.service.InspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/expert")
public class SearchlistController {

    @Autowired
    private ExpertListService expertListService;

    @GetMapping("/list")
    public String expertList(Model model){
        List<ExpertDTO> expertDto = expertListService.showExpertsDto();
        model.addAttribute("expertDto", expertDto);

        return "expertList";
    }


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

