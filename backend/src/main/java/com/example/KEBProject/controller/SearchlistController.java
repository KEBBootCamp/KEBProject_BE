package com.example.KEBProject.controller;



import com.example.KEBProject.dto.ExpertDTO;
import com.example.KEBProject.entity.User;
import com.example.KEBProject.service.ExpertListService;
import com.example.KEBProject.service.ExpertService;
import com.example.KEBProject.service.UserService;
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

    @GetMapping("/{engineerId}")
    public String expertInfo(@PathVariable String engineerId, Model model) {
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
    @GetMapping("/list")
    public String expertList(Model model){
        List<ExpertDTO> expertDto = expertListService.showExpertsDto();
        model.addAttribute("expertDto", expertDto);
        return "expertList";
    }
}

