package com.example.KEBProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "Inspection")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matching_id", nullable = false)
    private Integer matchingId;

    //customerid, engineerid 널여부 일단 true로 해놓았습니다
    //검수신청시 고객은 값을 입력하고 데이터에 저장하는 부분에서
    //엔지니어아이디가 널이라는 오류로인해
    @NonNull
    @Column(name = "customer_id", nullable = true, length = 20)
    private String customerId;

    @NonNull
    @Column(name = "engineer_id", nullable = true, length = 20)
    private String engineerId;

    @NonNull
    @Column(name = "model", nullable = false, length = 20)
    private String model;

    @NonNull
    @Column(name = "place", nullable = false, length = 20)
    private String place;

    @Column(name = "inspect_date")
    private Timestamp inspectDate;

    //널 여부 false로 해놔서 일단 false로 초기화 해놓았습니다.
    @NonNull
    @Column(name = "checked", nullable = false)
    private Boolean checked = false;

    @NonNull
    @Column(name = "complete", nullable = false)
    private Boolean complete = false;

    @NonNull
    @Column(name = "is_rate", nullable = false)
    private Boolean isRate = false;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "engineer_id", referencedColumnName = "engineer_id", insertable = false, updatable = false)
    private Expert engineer;
}