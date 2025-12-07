package com.example.room_reservation_system_api.controller.dto;

public record ReservationUserDataDTO(
    ReservationDTO reservation,
    String userName
) {
    
}
