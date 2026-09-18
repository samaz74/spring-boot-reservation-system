package com.app.reservation.controller;

import com.app.reservation.Service.ReservationService;
import com.app.reservation.dto.ReservationRequest;
import com.app.reservation.dto.ReservationResponse;
import com.app.reservation.models.enums.ReservationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @PostMapping("")
    public ReservationResponse createReservation(@RequestBody ReservationRequest reservation){
        return reservationService.createReservation(reservation);
    }

    @GetMapping("/{id}")
    public ReservationResponse getReservation(@PathVariable Long id){
        return reservationService.getReservationWithId(id);
    }

    @GetMapping("/search/myReservation")
    public List<ReservationResponse> getMyReservation(Principal principal){
        return reservationService.getCurrentUserReservations(principal);
    }
    @GetMapping("/resource/{resourceId}")
    public List<ReservationResponse> getReservationByResourceId(@PathVariable Long resourceId){
        return reservationService.getResourceReservations(resourceId);
    }
    @PutMapping("/{id}")
    public ReservationResponse changeStatus(@PathVariable Long id, ReservationStatus reservationStatus, Principal principal){
        return reservationService.changeStatus(id, reservationStatus, principal);
    }
}
