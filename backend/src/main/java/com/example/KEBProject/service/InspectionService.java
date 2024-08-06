package com.example.KEBProject.service;

import com.example.KEBProject.entity.User;
import com.example.KEBProject.repository.InspectionRepository;
import com.example.KEBProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.KEBProject.entity.Inspection;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;


@Service
public class InspectionService {


  private final InspectionRepository inspectionRepository;

  @Autowired
  public InspectionService(InspectionRepository inspectionRepository, UserRepository userRepository) {
    this.inspectionRepository = inspectionRepository;
  }

  public Inspection createInspection(Inspection inspection) {
    return inspectionRepository.save(inspection);
  }


  //전문가에게 검수요청왔을떄
  public List<Inspection> getInspectionsForExpert(String engineerId) {
    return inspectionRepository.findByEngineerId(engineerId);
  }

  //고객이 검수신청 할때
  public List<Inspection> getInspectionsForCustomer(String customerId) {
    return inspectionRepository.findByCustomerId(customerId);
  }

  //매칭 아이디
  //inspection_submit페이지를 위한 service
  public Inspection getInspectionById(int matchingId) {
    return inspectionRepository.findById(matchingId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid matching ID: " + matchingId));
  }


  //검수 요청 수락 여부
  public void updateInspectionChecked(int matchingId, boolean checked) {

    Inspection inspection = getInspectionById(matchingId);

    inspection.setChecked(checked);


    inspectionRepository.save(inspection);
  }

  //검수 완료 여부
  public void updateInspectionComplete(int matchingId, boolean complete) {

    Inspection inspection = getInspectionById(matchingId);

    inspection.setComplete(complete);

    inspectionRepository.save(inspection);
  }

//
//  //리뷰 쓸지 안쓸지
//  public void updateInspectionRate(int matchingId, boolean isRate) {
//    Inspection inspection = getInspectionById(matchingId);
//    inspection.setIsRate(isRate);
//    inspectionRepository.save(inspection);
//  }
}