package com.example.room_reservation_system_api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.example.room_reservation_system_api.entity.Reservation;

public interface ReservationQueryRepository extends Repository<Reservation, Long> {
    @Query("SELECT COUNT(r) > 0 FROM Reservation r " +
            "WHERE r.room.id = :roomId " +
            "AND r.startAt < :endAt " +
            "AND r.endAt > :startAt")
    public boolean existsOverlappingReservation(
            @Param("roomId") Long roomId,
            @Param("startAt") LocalDateTime startAt,
            @Param("endAt") LocalDateTime endAt);

    @Query("SELECT r FROM Reservation r " +
            "JOIN FETCH r.user " +
            "WHERE r.room.id = :roomId " +
            "AND r.startAt < :rangeEnd " +
            "AND r.endAt > :rangeStart " +
            "ORDER BY r.startAt ASC")
    public List<Reservation> findByRoomIdAndDateRange(
            @Param("roomId") Long roomId,
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("rangeEnd") LocalDateTime rangeEnd);

    @Query("SELECT r FROM Reservation r " +
            "JOIN FETCH r.room " +
            "WHERE r.user.uid = :uid " +
            "ORDER BY r.startAt DESC")
    public List<Reservation> findByUserId(@Param("uid") String uid);
}
