package com.example.KEBProject.dto;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class InspectionDTO {
  private Integer matchingId;
  private String customerId;
  private String engineerId;
  private String model;
  private String place;
  private Timestamp inspectDate;
  private Boolean checked;
  private Boolean complete;
  private Boolean isRate;

}
