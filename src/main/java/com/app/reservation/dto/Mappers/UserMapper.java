package com.app.reservation.dto.Mappers;

import com.app.reservation.dto.UserRequest;
import com.app.reservation.dto.UserResponse;
import com.app.reservation.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserRequest userRequest) {
        return new User(userRequest.getFirstName(),userRequest.getLastName(),userRequest.getEmail(),userRequest.getPassword(),userRequest.getRole(),userRequest.isActive());
    }
    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole(), user.isEnabled(), user.getCreatedAt())
;    }
}
