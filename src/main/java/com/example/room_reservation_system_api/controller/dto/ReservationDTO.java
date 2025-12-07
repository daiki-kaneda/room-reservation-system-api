package com.example.room_reservation_system_api.controller.dto;

import java.time.LocalDateTime;

public record ReservationDTO(
    Long id,
    LocalDateTime startAt,
    LocalDateTime endAt
) {
    
}
