package com.example.room_reservation_system_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.room_reservation_system_api.controller.dto.RoomDataDTO;
import com.example.room_reservation_system_api.repository.RoomQueryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomQueryRepository roomQueryRepository;

    public Page<RoomDataDTO> getRoomsByName(String name, Pageable pageable) {
        return roomQueryRepository.findByNameContaining(name, pageable)
                .map(r -> new RoomDataDTO(r.getId(), r.getName(), r.isActive()));
    }
}
