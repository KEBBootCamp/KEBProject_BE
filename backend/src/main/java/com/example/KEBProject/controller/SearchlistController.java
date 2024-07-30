package com.example.KEBProject.controller;



import com.example.KEBProject.dto.ExpertDTO;
import com.example.KEBProject.dto.InspectionDTO;
import com.example.KEBProject.entity.User;
import com.example.KEBProject.service.ExpertListService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/expert")
public class    SearchlistController {

    @Autowired
    private ExpertListService expertListService;

    //TC-5 엔지니어 목록
    @GetMapping("/list")
    public String tests(@ModelAttribute("inspectionDTO")InspectionDTO inspectionDTO, Model model) {
        model.addAttribute("inspectionDTO", inspectionDTO);

        List<ExpertDTO> expertDto = expertListService.showExpertsDto();
        model.addAttribute("expertDto", expertDto);

        return "expertList";
    }

    //TC-6 엔지니어 상세정보
    @PostMapping("/expertDetails")
    public String expertInfo(@RequestParam("userId") String userId,
                             @RequestParam("customerId") String customerId,
                             @RequestParam("model") String model,
                             @RequestParam("place") String place,
                             HttpSession session,  Model inspectionmodel) {
        User user = (User) session.getAttribute("user");

        inspectionmodel.addAttribute("customerId",user.getUserId());
        inspectionmodel.addAttribute("expertId",userId  );
        inspectionmodel.addAttribute("model",model);
        inspectionmodel.addAttribute("place",place);

        return "expertInfo";
    }

}

