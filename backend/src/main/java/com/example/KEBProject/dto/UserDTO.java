package com.example.KEBProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String userId;
    private String userPwd;
    private String userName;
    private String userEmail;
    private String userPhonenumber;
    private Boolean isExpert;
}