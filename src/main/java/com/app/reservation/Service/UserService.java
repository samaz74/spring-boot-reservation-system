package com.app.reservation.Service;

import com.app.reservation.Repository.UserRepository;
import com.app.reservation.dto.UserRequest;
import com.app.reservation.dto.UserResponse;
import com.app.reservation.dto.mappers.UserMapper;
import com.app.reservation.exception.InvalidOperationException;
import com.app.reservation.exception.ResourceNotFoundException;
import com.app.reservation.models.User;
import com.app.reservation.models.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public User findByUserIdEntity(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    public User findByEmailEntity(String email){
        return userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
    }
    public UserResponse findByUserId(Long userId) {
        return userRepository.findById(userId).map(userMapper::toResponse).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    public UserResponse findByEmail(String email){
        return userRepository.findByEmail(email).map(userMapper::toResponse).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
    }
    @Transactional
    public UserResponse changeUserActivation(Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        user.setEnabled(!user.isEnabled());
        return userMapper.toResponse(user);
    }
    @Transactional
    public UserResponse updateUser(UserRequest userRequest,Long id){
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        return userMapper.toResponse(user);
    }
    @Transactional
    public UserResponse changeUserRole(Long id , Role role){
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
        if (!user.getRole().equals(role)){
            user.setRole(role);
            return userMapper.toResponse(user);
        }else throw new InvalidOperationException("Invalid Operation");
    }
}
