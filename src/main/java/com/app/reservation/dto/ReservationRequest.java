package com.app.reservation.dto;

import com.app.reservation.models.enums.ReservationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequest {
    @NotNull
    private Long UserId;
    @NotNull
    private Long ResourceId;
    @NotNull
    private LocalDateTime StartTime;
    @NotNull
    private LocalDateTime EndTime;
}
