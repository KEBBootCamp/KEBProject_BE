package com.example.KEBProject.repository;


import com.example.KEBProject.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ExpertListRepository extends JpaRepository<Expert, String> {
    Optional<Expert> findByUser_UserId(String userId);
}
