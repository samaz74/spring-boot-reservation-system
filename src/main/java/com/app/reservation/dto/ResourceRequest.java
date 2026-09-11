package com.app.reservation.dto;

import com.app.reservation.models.enums.ResourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private ResourceType resourceType;
    @NotNull
    private int capacity;
    private boolean active = true;
}
