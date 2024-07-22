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

    @GetMapping("/list")
    public String expertList(Model model){
        List<ExpertDTO> expertDto = expertListService.showExpertsDto();
        model.addAttribute("expertDto", expertDto);
        return "expertList";
    }
}

