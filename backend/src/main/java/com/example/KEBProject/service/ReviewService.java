//package com.example.KEBProject.service;
//
//import com.example.KEBProject.entity.Review;
//import com.example.KEBProject.repository.ReviewRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//
//@Service
//public class ReviewService {
//
//  private final ReviewRepository reviewRepository;
//
//  @Autowired
//  public ReviewService(ReviewRepository reviewRepository) {
//    this.reviewRepository = reviewRepository;
//  }
//
//  //Inspection에서 고객이 israte 했을때만
//  public List<Reivew> getReviewsForCustomer(String customerId){
//    return reviewRepository.findByCustomerId(customerId);
//  }
//  //일단 후기- 좋은점
//  public Review writeReview(int matchingId, String ratingGood, String ratingBad, Timestamp reviewDate) {
//
//    Review review = new Review();
//
//    review.setRatingGood(ratingGood);
//    review.setRatingBad(ratingBad);
//    review.setReviewDate(Timestamp.valueOf(LocalDateTime.now()));
//
//    review.setMatchingId(matchingId);
//
//    return reviewRepository.save(review);
//  }
//}
