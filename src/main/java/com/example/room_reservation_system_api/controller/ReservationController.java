package com.example.room_reservation_system_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.room_reservation_system_api.controller.dto.ReservationCreateRequestDTO;
import com.example.room_reservation_system_api.controller.dto.ReservationRoomDataDTO;
import com.example.room_reservation_system_api.controller.dto.ReservationUserDataDTO;
import com.example.room_reservation_system_api.service.ReservationService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/rooms/{roomId}/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    public void createReservation(
            @AuthenticationPrincipal String uid,
            @PathVariable Long roomId,
            @RequestBody ReservationCreateRequestDTO request) {
        reservationService.reserve(
                uid,
                roomId,
                request.startAt(),
                request.endAt());
    }

    @DeleteMapping("/reservations/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelReservation(
            @PathVariable Long reservationId) {
        reservationService.cancel(reservationId);
    }

    @GetMapping("/my/reservations")
    public List<ReservationRoomDataDTO> getMyReservations(
            @AuthenticationPrincipal String uid) {
        return reservationService.getReservationsByUserId(uid);
    }

    @GetMapping("/rooms/{roomId}/reservations")
    public List<ReservationUserDataDTO> getReservationsByRoomIdAndDateRage(
            @PathVariable Long roomId,
            // URL例: /api/rooms/1/reservations?rangeStart=2025-12-09T10:00:00&rangeEnd=...
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime rangeStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime rangeEnd) {

        return reservationService.getReservationsByRoomIdAndDateRange(
                roomId,
                rangeStart,
                rangeEnd);
    }

}
