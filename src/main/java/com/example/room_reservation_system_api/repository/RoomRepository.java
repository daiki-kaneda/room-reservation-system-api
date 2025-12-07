package com.example.room_reservation_system_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.room_reservation_system_api.entity.Room;

public interface RoomRepository extends JpaRepository<Room,Long>{
    
}
