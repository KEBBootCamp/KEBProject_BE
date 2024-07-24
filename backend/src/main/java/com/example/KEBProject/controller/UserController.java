package com.example.KEBProject.controller;

import com.example.KEBProject.dto.ExpertDTO;
import com.example.KEBProject.dto.UserDTO;
import com.example.KEBProject.entity.Expert;
import com.example.KEBProject.entity.User;
import com.example.KEBProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUserPwd(userDTO.getUserPwd());
        user.setUserName(userDTO.getUserName());
        user.setUserNickname(userDTO.getUserNickname());
        user.setUserEmail(userDTO.getUserEmail());
        user.setUserPhonenumber(userDTO.getUserPhonenumber());
        user.setIsExpert(false); // 일반 사용자로 설정
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    @PostMapping("/expertjoin")
    public ResponseEntity<String> expertJoin(@RequestBody ExpertDTO expertDTO) {
        User user = new User();
        user.setUserId(expertDTO.getUserId());
        user.setUserPwd(expertDTO.getUserPwd());
        user.setUserName(expertDTO.getUserName());
        user.setUserNickname(expertDTO.getUserNickname());
        user.setUserEmail(expertDTO.getUserEmail());
        user.setUserPhonenumber(expertDTO.getUserPhonenumber());
        user.setIsExpert(true); // 전문가 사용자로 설정

        Expert expert = new Expert();
        expert.setEngineerId(expertDTO.getUserId());
        expert.setEngineerCareer(expertDTO.getEngineerCareer());
        expert.setEngineerPicture(expertDTO.getEngineerPicture());
        expert.setEngineerCertification(expertDTO.getEngineerCertification());
        expert.setEngineerProfile(expertDTO.getEngineerProfile());
        expert.setRatingAverage(expertDTO.getRatingAverage());
        expert.setUser(user);

        user.setExpert(expert);

        // 사용자와 전문가 엔터티 저장
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Expert user and details created successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDTO userDTO, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User user = userService.findById(userDTO.getUserId());
        if (user != null && user.getUserPwd().equals(userDTO.getUserPwd())) {
            session.setAttribute("user", user); // 세션에 사용자 정보 저장
            response.put("message", "Login successful");
            response.put("isExpert", user.getIsExpert());
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/showUsers")
    public List<User> showUsers() {
        return userService.showUsers();
    }

    //세션 확인 url
    @GetMapping("/show/sess")
    @ResponseBody
    public ResponseEntity<?> showSessionUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No session");
        } else {
            return ResponseEntity.ok(user);
        }
    }
}