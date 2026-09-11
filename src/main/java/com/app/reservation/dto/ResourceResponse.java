package com.app.reservation.dto;

import com.app.reservation.models.enums.ResourceType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceResponse {
    private Long id;
    private String name;
    private String description;
    private ResourceType resourceType;
    private int capacity;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
