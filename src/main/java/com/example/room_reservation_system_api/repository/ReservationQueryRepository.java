package com.example.room_reservation_system_api.repository;

import org.springframework.data.repository.Repository;

import com.example.room_reservation_system_api.entity.Reservation;

public interface ReservationQueryRepository extends Repository<Reservation,Long>{
    
}
