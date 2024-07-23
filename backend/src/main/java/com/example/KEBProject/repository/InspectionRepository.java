package com.example.KEBProject.repository;

import com.example.KEBProject.entity.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Integer> {

  List<Inspection> findByEngineerId(String engineerId);

  List<Inspection> findByCustomerId(String customerId);
}