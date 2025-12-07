package com.example.room_reservation_system_api.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

import com.example.room_reservation_system_api.controller.dto.ReservationDTO;
import com.example.room_reservation_system_api.controller.dto.ReservationRoomDataDTO;
import com.example.room_reservation_system_api.controller.dto.ReservationUserDataDTO;
import com.example.room_reservation_system_api.repository.ReservationQueryRepository;
import com.example.room_reservation_system_api.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationQueryRepository reservationQueryRepository;
    private final ReservationRepository reservationRepository;

    public List<ReservationUserDataDTO> getReservationsByRoomIdAndDateRange(
            Long roomId,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd) {
        return reservationQueryRepository.findByRoomIdAndDateRange(roomId, rangeStart, rangeEnd)
                .stream()
                .map(r -> new ReservationUserDataDTO(
                        new ReservationDTO(r.getId(), r.getStartAt(), r.getEndAt()),
                        r.getUser().getName()))
                .toList();
    };

    public List<ReservationRoomDataDTO> getReservationsByUserId(
            String uid) {
        return reservationQueryRepository.findByUserId(uid)
                .stream()
                .map(r -> new ReservationRoomDataDTO(
                        new ReservationDTO(r.getId(), r.getStartAt(), r.getEndAt()),
                        r.getRoom().getId(),
                        r.getRoom().getName(),
                        r.getRoom().isActive()))
                .toList();
    };

    private boolean checkOverlapping(Long roomId, LocalDateTime startAt, LocalDateTime endAt) {
        return reservationQueryRepository.existsOverlappingReservation(roomId, startAt, endAt);
    }
}
