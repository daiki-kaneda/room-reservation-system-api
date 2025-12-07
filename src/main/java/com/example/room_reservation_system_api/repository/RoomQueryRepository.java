package com.example.room_reservation_system_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.example.room_reservation_system_api.entity.Room;

public interface RoomQueryRepository extends Repository<Room,Long>{
    Page<Room> findByNameContaining(String name,Pageable pagenable);
}
