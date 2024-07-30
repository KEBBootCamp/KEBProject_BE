package com.example.KEBProject.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id", nullable = false, length = 20)
    private String userId;

    @NonNull
    @Column(name = "user_pwd", nullable = false, length = 20)
    private String userPwd;

    @NonNull
    @Column(name = "user_name", nullable = false, length = 20)
    private String userName;

    @NonNull
    @Column(name = "user_email", nullable = false, length = 20)
    private String userEmail;

    @NonNull
    @Column(name = "user_phonenumber", nullable = false, length = 20)
    private String userPhonenumber;

    @NonNull
    @Column(name = "is_expert", nullable = false)
    private Boolean isExpert;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Expert expert;
}