package com.example.KEBProject.repository;

import com.example.KEBProject.entity.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InspectionRepository extends JpaRepository<Inspection, Integer> {

  List<Inspection> findByEngineerId(String engineerId);

  List<Inspection> findByCustomerId(String customerId);

  List<Inspection> findByCustomerIdAndChecked(String customerId, boolean checked);
}