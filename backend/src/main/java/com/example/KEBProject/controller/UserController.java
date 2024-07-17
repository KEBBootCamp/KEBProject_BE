package com.example.KEBProject.controller;

import com.example.KEBProject.dto.ExpertDTO;
import com.example.KEBProject.dto.UserDTO;
import com.example.KEBProject.entity.Expert;
import com.example.KEBProject.entity.User;
import com.example.KEBProject.service.ExpertService;
import com.example.KEBProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExpertService expertService;

    @PostMapping("/join")
    public String join(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUserPwd(userDTO.getUserPwd());
        user.setUserName(userDTO.getUserName());
        user.setUserNickname(userDTO.getUserNickname());
        user.setUserEmail(userDTO.getUserEmail());
        user.setUserPhonenumber(userDTO.getUserPhonenumber());
        user.setIsExpert(false); // 일반 사용자로 설정
        userService.createUser(user);
        return "User created successfully";
    }

    @PostMapping("/expertjoin")
    public String expertJoin(@RequestBody ExpertDTO expertDTO) {
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

        return "Expert user and details created successfully.";
    }

    @GetMapping("/showUsers")
    public List<User> showUsers() {
        return userService.showUsers();
    }

    @GetMapping("/showExperts")
    public List<Expert> showExperts() {
        return expertService.showExperts();
    }
}