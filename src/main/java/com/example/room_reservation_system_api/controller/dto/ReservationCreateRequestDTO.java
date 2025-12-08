package com.example.room_reservation_system_api.controller.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ReservationCreateRequestDTO(
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime startAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime endAt) {
}