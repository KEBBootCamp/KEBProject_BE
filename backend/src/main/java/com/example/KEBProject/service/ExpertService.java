package com.example.KEBProject.service;

import com.example.KEBProject.entity.Expert;
import com.example.KEBProject.repository.ExpertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpertService {

    @Autowired
    private ExpertRepository expertRepository;

    public List<Expert> showExperts() {
        return expertRepository.findAll();
    }

    public Expert createExpert(Expert expert) {
        return expertRepository.save(expert);
    }

    public void deleteExpert(String engineerId) {
        expertRepository.deleteById(engineerId);
    }

    public Expert findById(String engineerId) {
        Optional<Expert> expert = expertRepository.findById(engineerId);
        return expert.orElse(null);
    }

    public Expert getExpertById(String engineerId) {
        return findById(engineerId);
    }

    public Expert updateExpert(Expert expert) {
        return expertRepository.save(expert);
    }
}