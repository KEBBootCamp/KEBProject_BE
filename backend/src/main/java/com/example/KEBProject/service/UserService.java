package com.example.KEBProject.service;

import com.example.KEBProject.entity.User;
import com.example.KEBProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> showUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user); // 저장된 User 객체 반환
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public User findById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    //유저 로그인 중복 방지
    public boolean existsByUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }
}