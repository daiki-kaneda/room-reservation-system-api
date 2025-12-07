package com.example.room_reservation_system_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.room_reservation_system_api.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation,Long>{

    
}
