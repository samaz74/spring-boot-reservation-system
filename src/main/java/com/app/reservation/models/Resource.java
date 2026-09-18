package com.app.reservation.models;

import com.app.reservation.models.enums.ResourceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private ResourceType resourceType;
    private int capacity;
    private boolean active;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Resource(String name, String description, ResourceType resourceType, int capacity, boolean active) {
        this.name = name;
        this.description = description;
        this.resourceType = resourceType;
        this.capacity = capacity;
        this.active = active;
    }
}
