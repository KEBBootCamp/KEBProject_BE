package com.example.KEBProject.repository;


import com.example.KEBProject.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertListRepository extends JpaRepository<Expert, String> {
}
