package com.example.KEBProject.entity;

public enum Rating {
  //별 반개 어떻게 구현해야할지 감이 안잡혀 소수점 단위 없앴습니다.
  //https://velog.io/@hellocdpa/220305-%EB%A6%AC%EB%B7%B0-%EB%B3%84%EC%A0%90-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0
  RATING_1("1"),
  RATING_2("2"),
  RATING_3("3"),
  RATING_4("4"),
  RATING_5("5");

  private final String value;

  Rating(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static Rating fromValue(String value) {
    for (Rating rating : Rating.values()) {
      if (rating.getValue().equals(value)) {
        return rating;
      }
    }
    throw new IllegalArgumentException("Unknown value: " + value);
  }
}