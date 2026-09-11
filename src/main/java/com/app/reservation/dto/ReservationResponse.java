package com.app.reservation.dto;

import com.app.reservation.models.enums.ReservationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponse {
    private Long id;
    private Long UserId;
    private String UserName;
    private Long ResourceId;
    private String ResourceName;
    private LocalDateTime StartTime;
    private LocalDateTime EndTime;
    private ReservationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
