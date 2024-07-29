package com.example.KEBProject.controller;



import com.example.KEBProject.dto.ExpertDTO;
import com.example.KEBProject.service.ExpertListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/expert")
public class SearchlistController {

    @Autowired
    private ExpertListService expertListService;

    //TC-5 엔지니어 목록
    @GetMapping("/list")
    public String expertList(Model model){
        List<ExpertDTO> expertDto = expertListService.showExpertsDto();
        model.addAttribute("expertDto", expertDto);
        return "expertList";
    }




    //TC-6 엔지니어 상세정보
    @GetMapping("/{engineerId}")
    public String expertInfo(@PathVariable("engineerId") String engineerId, Model model) {
        try {
            ExpertDTO expertDto = expertListService.findByUserId(engineerId);
            model.addAttribute("expertDTO", expertDto);
            if (expertDto == null) {
                return "expertNotFound";
            }
            return "expertInfo";
        } catch (Exception e) {

            e.printStackTrace();
            return "errorPage";
        }
    }

}

