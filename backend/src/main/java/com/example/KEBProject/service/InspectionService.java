package com.example.KEBProject.service;

import com.example.KEBProject.repository.InspectionRepository;
import com.example.KEBProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.KEBProject.entity.Inspection;

import java.util.List;

@Service
public class InspectionService {


  private final InspectionRepository inspectionRepository;

  @Autowired
  public InspectionService(InspectionRepository inspectionRepository, UserRepository userRepository) {
    this.inspectionRepository = inspectionRepository;
  }


  //전문가에게 검수요청왔을떄
  public List<Inspection> getInspectionsForExpert(String engineerId) {
    return inspectionRepository.findByEngineerId(engineerId);
  }

  //고객이 검수신청 할때
  public List<Inspection> getInspectionsForCustomer(String customerId) {
    return inspectionRepository.findByCustomerId(customerId);
  }


  //inspection_submit페이지를 위한 service
  public Inspection getInspectionById(int matchingId) {
    return inspectionRepository.findById(matchingId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid matching ID: " + matchingId));
  }

}