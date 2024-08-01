package com.example.KEBProject.controller;

import com.example.KEBProject.dto.InspectionDTO;
import com.example.KEBProject.entity.Inspection;
import com.example.KEBProject.entity.User;
import com.example.KEBProject.service.ExpertService;
import com.example.KEBProject.service.InspectionService;
import com.example.KEBProject.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class InspectionController {

  private final InspectionService inspectionService;
  private final UserService userService;
  private final ExpertService expertService;

  @Autowired
  public InspectionController(InspectionService inspectionService, UserService userService, ExpertService expertService ) {
    this.inspectionService = inspectionService;
    this.userService =  userService;
    this.expertService =  expertService;
  }

  //TC-4 검수 조건 검색
  @GetMapping("/matching")
  public String showInspectionForm(Model model, HttpSession session) {
    //세션에서 가져옴
    User currentUser = (User) session.getAttribute("user");

    if (currentUser == null) {
      // 유저가 null일 경우에 대한 처리 (예: 로그인 페이지로 리다이렉트)
      return "/login";
    }

    model.addAttribute("userId", currentUser.getUserId());

    //엔지니어 검수 수락 리스트
    if (currentUser.getIsExpert()) {
      List<Inspection> inspections = inspectionService.getInspectionsForExpert(currentUser.getUserId());

      //검수 요청 응답한 것은 목록에서 사라지도록 설정
      List<Inspection> acceptInspections = inspections.stream()
          .filter(inspection -> inspection.getChecked() == null)
          .toList();
      model.addAttribute("inspections", acceptInspections);

      return "engineer_requested";

    }
    //엔지니어 프로필 리스트
    else {
      List<Inspection> inspections = inspectionService.getInspectionsForCustomer(currentUser.getUserId());
      model.addAttribute("inspections", inspections);
      return "inspection_form";
    }
  }

  //TC -4 검수 조건 검색 전달
  @PostMapping("/inspectionInfo")
  public String submitInspectionForm(@RequestParam("customerId") String customerId,
                                     @RequestParam("brand") String brand,
                                     @RequestParam("model") String model,
                                     @RequestParam("place") String place,
                                     @RequestParam("inspectDateTime") String inspectDateTime,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
    if (customerId == null || customerId.isEmpty() ||
            model == null || model.isEmpty() ||
            brand == null || brand.isEmpty() ||
            place == null || place.isEmpty())
//            || inspectDateTime == null || inspectDateTime.isEmpty())
    {
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

    redirectAttributes.addFlashAttribute("inspectionDTO", inspectionDTO);
    return "redirect:/expert/list";
  }

  // 엔지니어 검수 요청 상세보기
  @GetMapping("/inspection_detail/{matchingId}")
  public String checkInspection(@PathVariable int matchingId, Model model) {
    Inspection inspection = inspectionService.getInspectionById(matchingId);

    if (inspection != null) {
      model.addAttribute("inspection", inspection);
      return "inspection_detail";
    } else {
      return "redirect:/matching";
    }
  }

  // 엔지니어 검수 수락 여부
  @PostMapping("/matching/request/accept/{matchingId}")
  @ResponseBody
  public Map<String, String> checkInspection(@PathVariable int matchingId, @RequestBody Map<String,Boolean> request) {

    Inspection inspection = inspectionService.getInspectionById(matchingId);
    if (inspection != null){
      inspectionService.updateInspectionChecked(matchingId, true);

      return Map.of("status","accept");
    }

    return Map.of("status","failed");

  }

  //엔지니어 검수 요청 거절시 리스트에 나오지 않게
  @PostMapping("/matching/request/reject/{matchingId}")
  @ResponseBody
  public Map<String, String> rejectInspection(@PathVariable int matchingId, @RequestBody Map<String,Boolean> request) {
    Inspection inspection = inspectionService.getInspectionById(matchingId);
    if (inspection != null){
      inspectionService.rejectInspectionChecked(matchingId);
      return Map.of("status","rejected");
    }

    return Map.of("status","failed");
  }

  //수락한 검수 요청 리스트 목록 출력
  @GetMapping("/matching/accept/list")
  public String showAcceptRequested(Model model , HttpSession session) {
    User currentUser = (User) session.getAttribute("user");

    if (currentUser == null || !currentUser.getIsExpert()) {
      return "redirect:/login";
    }

    //전체 리스트 출력부분
    List<Inspection> inspections = inspectionService.getInspectionsForExpert(currentUser.getUserId());

    //수락한 검수요청만 리스트에 나오게하는 부분
    List<Inspection> acceptInspections = inspections.stream()
            .filter(inspection -> Boolean.TRUE.equals(inspection.getChecked()))
                .toList();
    model.addAttribute("inspections", acceptInspections);
    return "accept_requested";
  }


  //수락한 검수 요청 상세 -> complete 여부 확인하는 버튼
  @GetMapping("/accept_detail/{matchingId}")
  public String detailInspection(@PathVariable int matchingId, Model model) {
    Inspection inspection = inspectionService.getInspectionById(matchingId);

    if (inspection != null) {
      model.addAttribute("inspection", inspection);
      return "accept_detail";
    } else {
      return "redirect:/accept_requested";
    }
  }

  //엔지니어 검수 완료(Complete)
  @PostMapping("/matching/request/complete/{matchingId}")
  @ResponseBody
  public Map<String, String> completeInspection(@PathVariable int matchingId, @RequestBody Map<String,Boolean> request) {

    Inspection inspection = inspectionService.getInspectionById(matchingId);
    if (inspection != null){
      inspectionService.updateInspectionComplete(matchingId, true);

      return Map.of("status","complete");
    }

    return Map.of("status","failed");

  }

  //검수완료된 목록 고객이 확인
  @GetMapping("/matching/end")
  public String showEndRequested(Model model , HttpSession session) {
    User currentUser = (User) session.getAttribute("user");

    if (currentUser == null) {
      return "redirect:login";
    }

    List<Inspection> endInspections = inspectionService.getEndInspection(currentUser.getUserId());
    model.addAttribute("inspections", endInspections);

    return "end_requested";
  }

}