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
  private final UserRepository userRepository;

  @Autowired
  public InspectionService(InspectionRepository inspectionRepository, UserRepository userRepository) {
    this.inspectionRepository = inspectionRepository;
    this.userRepository = userRepository;
  }


  //전문가에게 검수요청왔을떄
  public List<Inspection> getInspectionsForExpert(String engineerId) {
    return inspectionRepository.findByEngineerId(engineerId);
  }

  //고객이 검수신청 할때
  public List<Inspection> getInspectionsForCustomer(String customerId) {
    return inspectionRepository.findByCustomerId(customerId);
  }

  //검수 신청부분
  @Transactional
  public Inspection createInspection(String userId, String model, String place, Timestamp inspectDate) {

    Inspection inspection = new Inspection();
    inspection.setModel(model);
    inspection.setPlace(place);
    inspection.setInspectDate(inspectDate);

    inspection.setCustomerId(userId);

    return inspectionRepository.save(inspection);
  }

  //매칭 아이디
  public Inspection getInspectionById(int matchingId) {
    return inspectionRepository.findById(matchingId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid matching ID: " + matchingId));
  }


  //검수 수락 여부
  public void updateInspectionChecked(int matchingId, boolean checked) {

    Inspection inspection = getInspectionById(matchingId);

    inspection.setChecked(checked);

    inspectionRepository.save(inspection);
  }
}