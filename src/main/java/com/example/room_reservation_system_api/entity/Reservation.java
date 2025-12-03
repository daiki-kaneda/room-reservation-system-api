package com.example.room_reservation_system_api.entity;

import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "uid")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    private LocalDateTime startAt;
    private LocalDateTime endAt;

    public Duration getDuration() {
        return Duration.between(startAt, endAt);
    }

    public static Reservation create(User user, Room room, LocalDateTime startAt, LocalDateTime endAt) {
        if (startAt.isAfter(endAt)) {
            throw new IllegalArgumentException("開始時刻は終了時刻よりも前である必要があります");
        }
        Reservation reservation = new Reservation();
        reservation.user = user;
        reservation.room = room;
        reservation.startAt = startAt;
        reservation.endAt = endAt;
        return reservation;
    }

    @Override
    public Long getId() {
        return id;
    }
}
