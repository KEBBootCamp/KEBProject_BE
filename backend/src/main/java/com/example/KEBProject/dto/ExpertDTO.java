package com.example.KEBProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpertDTO {
    private String userId;
    private String userPwd;
    private String userName;
    private String userNickname;
    private String userEmail;
    private String userPhonenumber;
    private Boolean isExpert;
    private Integer engineerCareer;
    private String engineerPicture;
    private String engineerCertification;
    private String engineerProfile;
    private Float ratingAverage;
}