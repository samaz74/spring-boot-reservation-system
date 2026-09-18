package com.app.reservation.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "invalidated_token")
public class InvalidatedToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private String email;
    private LocalDateTime expiresAt;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public InvalidatedToken(String token, String email, LocalDateTime expiresAt) {
        this.token = token;
        this.email = email;
        this.expiresAt = expiresAt;
    }
}
