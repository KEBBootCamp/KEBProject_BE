package com.example.KEBProject.controller;

import com.example.KEBProject.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Map;

@Controller
@RequestMapping("/review")
public class ReviewController {

  private final ReviewService reviewService;

  @Autowired
  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  // 리뷰 작성 페이지를 반환
  @GetMapping("/write/{matchingId}")
  public String showWriteReviewPage(@PathVariable int matchingId, Model model) {
    model.addAttribute("matchingId", matchingId);
    return "writeReview";
  }

  // 리뷰 작성 요청 처리
  @PostMapping("/write/{matchingId}")
  public ResponseEntity<Map<String, String>> writeReview(
      @PathVariable int matchingId,
      @RequestBody Map<String, String> reviewData) {

    String ratingGood = reviewData.get("rating_good");
    String ratingBad = reviewData.get("rating_bad");
    String ratingValue = reviewData.get("reviewStar");

    if (ratingValue == null) {
      return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Missing required fields"));
    }

    try {
      reviewService.writeReview(matchingId, ratingGood, ratingBad, ratingValue);
      return ResponseEntity.ok(Map.of("status", "success", "message", "리뷰가 성공적으로 작성되었습니다."));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "잘못된 평점 값입니다."));
    } catch (Exception e) {
      return ResponseEntity.status(500).body(Map.of("status", "error", "message", "리뷰 작성 중 오류가 발생했습니다."));
    }
  }
}