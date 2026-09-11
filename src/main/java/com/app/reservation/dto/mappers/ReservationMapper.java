package com.app.reservation.dto.Mappers;

import com.app.reservation.Service.ResourceService;
import com.app.reservation.Service.UserService;
import com.app.reservation.dto.ReservationRequest;
import com.app.reservation.dto.ReservationResponse;
import com.app.reservation.models.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;
    public Reservation toEntity(ReservationRequest reservationRequest) {
        return new Reservation(userService.findByUserIdEntity(reservationRequest.getUserId()),
                resourceService.findResourceByIdEntity(reservationRequest.getResourceId()),
                reservationRequest.getStartTime(),
                reservationRequest.getEndTime(),
                reservationRequest.getStatus()
                );
    }
    public ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(reservation.getId(),
                reservation.getUser().getId(),
                reservation.getUser().getFirstName().concat(" " + reservation.getUser().getLastName()),
                reservation.getResource().getId(),
                reservation.getResource().getName(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getStatus(),
                reservation.getCreatedAt(),
                reservation.getUpdatedAt());
    }
}
