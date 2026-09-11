package com.app.reservation.Service;

import com.app.reservation.Repository.ResourceRepository;
import com.app.reservation.dto.ReservationRequest;
import com.app.reservation.exception.ResourceNotFoundException;
import com.app.reservation.models.Reservation;
import com.app.reservation.models.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;
    public Resource findResourceByIdEntity(Long resourceId) {
        return resourceRepository.findById(resourceId).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }
}
