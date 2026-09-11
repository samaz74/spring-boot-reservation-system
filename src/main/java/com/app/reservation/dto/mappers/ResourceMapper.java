package com.app.reservation.dto.mappers;

import com.app.reservation.dto.ResourceRequest;
import com.app.reservation.dto.ResourceResponse;
import com.app.reservation.models.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResourceMapper {
    public Resource toEntity(ResourceRequest resourceRequest) {
        return new Resource(resourceRequest.getName(),
                resourceRequest.getDescription(),
                resourceRequest.getResourceType(),
                resourceRequest.getCapacity(),
                resourceRequest.isActive());
    }
    public ResourceResponse toResponse(Resource resource) {
        return new ResourceResponse(resource.getId(),
                resource.getName(),
                resource.getDescription(),
                resource.getResourceType(),
                resource.getCapacity(),
                resource.isActive(),
                resource.getCreatedAt(),
                resource.getUpdatedAt());
    }
}
