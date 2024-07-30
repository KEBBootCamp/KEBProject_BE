package com.example.KEBProject.dto;

import com.example.KEBProject.entity.User;
import com.example.KEBProject.entity.Expert;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpertDTO {
    private String userId;
    private String userName;
    private String userPwd;
    private String userNickname;
    private String userEmail;
    private String userPhonenumber;
    private Boolean isExpert;
    private Integer engineerCareer;
    private String engineerPicture;
    private String engineerCertification;
    private String engineerProfile;
//    private Float ratingAverage;
    private String engineerId;

    private ExpertDTO() {}

    public ExpertDTO(Expert expertEntity, User userEntity){
        this.userId=userEntity.getUserId();
        this.userPwd=userEntity.getUserPwd();
        this.userName=userEntity.getUserName();
        this.userNickname= userEntity.getUserNickname();
        this.userEmail=userEntity.getUserEmail();
        this.userPhonenumber=userEntity.getUserPhonenumber();
        this.isExpert=userEntity.getIsExpert();

        this.engineerCareer=expertEntity.getEngineerCareer();
        this.engineerPicture=expertEntity.getEngineerPicture();
        this.engineerCertification=expertEntity.getEngineerCertification();
        this.engineerProfile=expertEntity.getEngineerProfile();
//        this.ratingAverage=expertEntity.getRatingAverage();
    }
}
