package com.app.reservation.dto.mappers;

import com.app.reservation.dto.ReservationRequest;
import com.app.reservation.dto.ReservationResponse;
import com.app.reservation.models.Reservation;
import com.app.reservation.models.Resource;
import com.app.reservation.models.User;
import com.app.reservation.models.enums.ReservationStatus;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public Reservation toEntity(ReservationRequest reservationRequest, User user, Resource resource) {
        return new Reservation(user,
                resource,
                reservationRequest.getStartTime(),
                reservationRequest.getEndTime(),
                ReservationStatus.PENDING
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
