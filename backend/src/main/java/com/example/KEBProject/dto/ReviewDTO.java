package com.example.KEBProject.dto;

import com.example.KEBProject.entity.Inspection;
import com.example.KEBProject.entity.Rating;
import com.example.KEBProject.entity.Review;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ReviewDTO {
  private Integer matchingId;
  private Rating rating;
  private String ratingGood;
  private String ratingBad;
  private Timestamp reviewDate;

  private ReviewDTO() { }

  public ReviewDTO(Inspection inspectionEntity, Review reviewEntity) {
    this.matchingId = inspectionEntity.getMatchingId();

    this.rating = reviewEntity.getRating();
    this.ratingGood = reviewEntity.getRatingGood();
    this.ratingBad = reviewEntity.getRatingBad();
    this.reviewDate = reviewEntity.getReviewDate();
  }
}
