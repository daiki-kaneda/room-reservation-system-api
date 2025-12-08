package com.example.room_reservation_system_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.room_reservation_system_api.controller.dto.RoomDataDTO;
import com.example.room_reservation_system_api.service.RoomService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RoomController {
    private final RoomService roomService;
    @GetMapping("/rooms")
    public Page<RoomDataDTO> getRooms(
        @RequestParam String name,
        @PageableDefault(size = 20) Pageable pageable
    ) {
        return roomService.getRoomsByName(name, pageable);
    }
}
