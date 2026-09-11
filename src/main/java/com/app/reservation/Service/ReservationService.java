package com.app.reservation.Service;

import com.app.reservation.Repository.ReservationRepository;
import com.app.reservation.dto.ReservationRequest;
import com.app.reservation.dto.ReservationResponse;
import com.app.reservation.dto.mappers.ReservationMapper;
import com.app.reservation.exception.InvalidOperationException;
import com.app.reservation.models.Reservation;
import com.app.reservation.models.Resource;
import com.app.reservation.models.User;
import com.app.reservation.models.enums.ReservationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationMapper reservationMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;


    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        User user = userService.findByUserIdEntity(reservationRequest.getUserId());
        Resource resource = resourceService.findResourceByIdEntity(reservationRequest.getResourceId());
        Reservation reservation = reservationMapper.toEntity(reservationRequest,user,resource);
        //Rule1: Resource Must be active
        if(resource.isActive()){
           //Rule2: endTime > startTime
            if(reservation.getStartTime().isBefore(reservation.getEndTime())){
                //Rule3: startTime > now
                if(reservation.getStartTime().isAfter(LocalDateTime.now())){
                    //Rule4: Reservation must be smaller than 4H
                    if(Duration.between(reservation.getStartTime(), reservation.getEndTime()).toMinutes() <= 240){
                        //Rule5: check reservation for time selected
                        List<Reservation> existingReservations = reservationRepository.findByResourceAndStartTimeBeforeAndEndTimeAfter(resource,reservation.getEndTime(),reservation.getStartTime());
                        if(!existingReservations.isEmpty()) {
                            if (existingReservations.stream().filter(reservation1 -> reservation1.getStatus() == ReservationStatus.PENDING || reservation1.getStatus() == ReservationStatus.CONFIRMED).count() > 0) {
                                throw new InvalidOperationException("for this time already reservation exists");
                            } else {
                                reservationRepository.save(reservation);
                                return reservationMapper.toResponse(reservation);
                            }
                        }else{
                                reservationRepository.save(reservation);
                                return reservationMapper.toResponse(reservation);
                        }

                    }else throw new InvalidOperationException("Reservation duration must not exceed 4 hours");
                }else throw new InvalidOperationException("Start time must be after now");
            }else throw new InvalidOperationException("start time must be before end time");
        }else throw new InvalidOperationException("Resource is not active");
    }
}
