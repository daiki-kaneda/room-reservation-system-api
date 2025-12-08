package com.example.room_reservation_system_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.room_reservation_system_api.controller.dto.UserLoginRequestDTO;
import com.example.room_reservation_system_api.controller.dto.UserLoginResponseDTO;
import com.example.room_reservation_system_api.entity.User;
import com.example.room_reservation_system_api.service.UserService;
import com.google.firebase.auth.FirebaseAuthException;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public UserLoginResponseDTO loginOrSignUp(@RequestBody UserLoginRequestDTO request) throws FirebaseAuthException{
        User user = userService.loadOrCreateUser(request.idToken());
        return new UserLoginResponseDTO(user.getUid());
    }
    
}
