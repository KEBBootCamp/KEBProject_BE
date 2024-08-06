package com.example.KEBProject.service;

import com.example.KEBProject.dto.ExpertDTO;
import com.example.KEBProject.entity.Expert;

import com.example.KEBProject.entity.User;
import com.example.KEBProject.repository.ExpertListRepository;
import com.example.KEBProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpertListService {

    @Autowired
    private ExpertListRepository expertListRepository;
    @Autowired
    private UserRepository userRepository;

    public List<ExpertDTO> showExpertsDto(String brand) {
        List<Expert> experts = expertListRepository.findAll();

        return experts.stream()
                .map(expert -> {
                    Optional<User> user = userRepository.findById(expert.getEngineerId());
                    return user.map(value -> new ExpertDTO(expert, value))
                            .orElse(null);
                })
                .filter(expertDto -> expertDto != null && brand.equals(expertDto.getEngineerBrand()))
                .sorted(Comparator.comparing(ExpertDTO::getEngineerCareer).reversed()) // engineerCareer 기준으로 내림차순 정렬
                .collect(Collectors.toList());
    }


    public Optional<ExpertDTO> findByUserId(String userId) {
        Optional<Expert> expert = expertListRepository.findByUser_UserId(userId);
        Optional<User> user = userRepository.findById(userId);

        if (expert.isPresent() && user.isPresent()) {
            Expert expertEntity = expert.get();
            User userEntity = user.get();
            ExpertDTO expertDTO = new ExpertDTO(expertEntity, userEntity);
            return Optional.of(expertDTO);
        } else {
            return Optional.empty();
        }
    }


}
