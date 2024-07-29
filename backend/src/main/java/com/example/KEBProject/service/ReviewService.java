package com.example.KEBProject.service;

import com.example.KEBProject.entity.Rating;
import com.example.KEBProject.entity.Review;
import com.example.KEBProject.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

  private final ReviewRepository reviewRepository;

  @Autowired
  public ReviewService(ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  //일단 후기
  public Review writeReview(int matchingId, String ratingGood, String ratingBad, String ratingValue) {



    Rating rating;

    rating = Rating.fromValue(ratingValue);

    Review review = new Review();

    review.setRatingGood(ratingGood);
    review.setRating(rating);
    review.setRatingBad(ratingBad);

    review.setReviewDate(Timestamp.valueOf(LocalDateTime.now()));

    review.setMatchingId(matchingId);

    return reviewRepository.save(review);
  }
}
