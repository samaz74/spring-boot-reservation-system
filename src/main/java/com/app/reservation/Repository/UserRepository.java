package com.app.reservation.Repository;

import com.app.reservation.models.User;
import com.app.reservation.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByEmailContaining(String email);
    List<User> findByRole(Role role);

}
