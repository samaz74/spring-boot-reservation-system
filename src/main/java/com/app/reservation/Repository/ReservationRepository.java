package com.app.reservation.Repository;

import com.app.reservation.models.Reservation;
import com.app.reservation.models.Resource;
import com.app.reservation.models.User;
import com.app.reservation.models.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(User user);
    List<Reservation> findByResource(Resource resource);
    List<Reservation> findByResourceAndStartTimeBeforeAndEndTimeAfter(Resource resource,  LocalDateTime requestedEnd, LocalDateTime requestedStart);

    List<Reservation> findByStatus(ReservationStatus status);

    Long id(Long id);
}
