package com.example.KEBProject.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Expert")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expert {

    @Id
    @Column(name = "engineer_id", nullable = false, length = 20)
    private String engineerId;

    //엔지니어 경력
    @Column(name = "engineer_career")
    private Integer engineerCareer;

    //프로필 사진
    @Column(name = "engineer_picture", length = 80)
    private String engineerPicture;

    //엔지니어 주력 제조사
    @Column(name = "engineer_brand", length = 80)
    private String engineerBrand;

    //한줄 소개
    @Column(name = "engineer_profile", length = 200)
    private String engineerProfile;



    @OneToOne
    @MapsId
    @JoinColumn(name = "engineer_id")
//    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonBackReference
    private User user;
}