package com.example.room_reservation_system_api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.room_reservation_system_api.controller.dto.ReservationDTO;
import com.example.room_reservation_system_api.controller.dto.ReservationRoomDataDTO;
import com.example.room_reservation_system_api.controller.dto.ReservationUserDataDTO;
import com.example.room_reservation_system_api.entity.Reservation;
import com.example.room_reservation_system_api.entity.Room;
import com.example.room_reservation_system_api.entity.User;
import com.example.room_reservation_system_api.repository.ReservationQueryRepository;
import com.example.room_reservation_system_api.repository.ReservationRepository;
import com.example.room_reservation_system_api.repository.RoomQueryRepository;
import com.example.room_reservation_system_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
        private final ReservationQueryRepository reservationQueryRepository;
        private final ReservationRepository reservationRepository;
        private final UserRepository userRepository;
        private final RoomQueryRepository roomQueryRepository;

        @Transactional
        public Long reserve(
                        String uid, Long roomId, LocalDateTime startAt, LocalDateTime endAt) {
                boolean isOverlapping = checkOverlapping(roomId, startAt, endAt);
                if (isOverlapping) {
                        throw new IllegalStateException("指定された時間にはすでに予約が入っています。");
                } else {
                        User user = userRepository.findById(uid)
                                        .orElseThrow(() -> new IllegalArgumentException("User not found"));
                        Room room = roomQueryRepository.findById(roomId)
                                        .orElseThrow(() -> new IllegalArgumentException("Room not found"));
                        Reservation newReservation = Reservation.create(user, room, startAt, endAt);
                        reservationRepository.save(newReservation);
                        return newReservation.getId();
                }
        }

        @Transactional
        public Long cancel(Long reservationId) {
                if (!reservationRepository.existsById(reservationId)) {
                        throw new IllegalArgumentException("予約が見つかりません");
                }
                reservationRepository.deleteById(reservationId);
                return reservationId;
        }

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
