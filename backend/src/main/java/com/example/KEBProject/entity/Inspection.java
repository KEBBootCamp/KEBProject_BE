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
  @Column(name = "model", nullable = false, length = 20)
  private String model;

  @NonNull
  @Column(name = "place", nullable = false, length = 20)
  private String place;

  //임시로 null 값 허용 -- 수정 예정
  @Column(name = "inspect_date", nullable = true)
  private Timestamp inspectDate;

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