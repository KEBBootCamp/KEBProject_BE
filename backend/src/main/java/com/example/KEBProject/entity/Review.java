package com.example.KEBProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

  @Id
  @Column(name = "matching_id", nullable = false)
  private Integer matchingId;

  @NonNull
  @Enumerated(EnumType.STRING)
  @Column(name = "rating", nullable = false)
  private Rating rating;

  @Column(name = "rating_good",nullable = true, length = 100)
  private String ratingGood;

  @Column(name = "rating_bad", nullable = true, length = 100)
  private String ratingBad;

  @NonNull
  @Column(name = "review_date", nullable = false)
  private Timestamp reviewDate;

  @OneToOne
  @JoinColumn(name = "matching_id", referencedColumnName = "matching_id", insertable = false, updatable = false)
  private Inspection inspection;
}