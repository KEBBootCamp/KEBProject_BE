package com.example.KEBProject.dto;

import com.example.KEBProject.entity.Rating;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDTO {
  private Rating rating;

  public RatingDTO() { }

  public RatingDTO(Rating rating) {
    this.rating = rating;
  }

}
