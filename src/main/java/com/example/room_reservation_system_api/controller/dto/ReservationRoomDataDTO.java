package com.example.room_reservation_system_api.controller.dto;

public record ReservationRoomDataDTO(
    ReservationDTO reservation,
    Long roomId,
    String roomName,
    boolean isActive
) {
    
}
