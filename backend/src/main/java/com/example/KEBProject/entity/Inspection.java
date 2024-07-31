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

  @NonNull
  @Column(name = "customer_id", nullable = false, length = 20)
  private String customerId;

  @NonNull
  @Column(name = "engineer_id", nullable = true, length = 20)
  private String engineerId;

  @NonNull
  @Column(name = "brand", nullable = false, length = 20)
  private String brand;

  @NonNull
  @Column(name = "model", nullable = false, length = 20)
  private String model;

  @NonNull
  @Column(name = "place", nullable = false, length = 20)
  private String place;

  //임시로 null 값 허용 -- 수정 예정
  @Column(name = "inspect_date", nullable = true)
  private Timestamp inspectDate;

  //엔지니어의 검수 수락 여부
  //엔지니어가 수락 , 거절 둘다안하는 상태가 있을 수 있으므로 널을 허용했습니다
  @NonNull
  @Column(name = "checked", nullable = true)
  private Boolean checked ;

  //검수 완료 여부
  @NonNull
  @Column(name = "complete", nullable = false)
  private Boolean complete = false;

  @ManyToOne
  @JoinColumn(name = "customer_id", referencedColumnName = "user_id", insertable = false, updatable = false)
  private User customer;

  @ManyToOne
  @JoinColumn(name = "engineer_id", referencedColumnName = "engineer_id", insertable = false, updatable = false)
  private Expert engineer;
}