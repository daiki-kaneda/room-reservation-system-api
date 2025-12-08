package com.example.room_reservation_system_api.controller.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
    String error,
    String message,
    LocalDateTime timeStamp
) {
    public ErrorResponseDTO(String error, String message) {
        this(error, message, LocalDateTime.now());
    }
}
