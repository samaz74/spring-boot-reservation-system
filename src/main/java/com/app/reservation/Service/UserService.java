package com.app.reservation.Service;

import com.app.reservation.Repository.UserRepository;
import com.app.reservation.exception.ResourceNotFoundException;
import com.app.reservation.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User findByUserIdEntity(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
