package com.example.KEBProject.repository;

import com.example.KEBProject.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpertRepository extends JpaRepository<Expert, String> {
}