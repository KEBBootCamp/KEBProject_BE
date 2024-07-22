package com.example.KEBProject.service;

import com.example.KEBProject.dto.ExpertDTO;
import com.example.KEBProject.entity.Expert;

import com.example.KEBProject.entity.User;
import com.example.KEBProject.repository.ExpertListRepository;
import com.example.KEBProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpertListService {

    @Autowired
    private ExpertListRepository expertListRepository;
    @Autowired
    private UserRepository userRepository;

    public ExpertListService(ExpertListRepository expertListRepository, UserRepository userRepository) {
        this.expertListRepository = expertListRepository;
        this.userRepository = userRepository;
    }

    public List<ExpertDTO> showExpertsDto() {
        List<Expert> experts = expertListRepository.findAll();

        return experts.stream()
                .map(expert -> {
                    Optional<User> user = userRepository.findById(expert.getEngineerId());
                    return user.map(value -> new ExpertDTO(expert, value))
                            .orElse(null); // or handle the case where user is not found
                })
                .collect(Collectors.toList());
    }
}
