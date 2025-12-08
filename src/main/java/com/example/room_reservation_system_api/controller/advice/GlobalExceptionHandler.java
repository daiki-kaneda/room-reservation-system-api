package com.example.room_reservation_system_api.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.room_reservation_system_api.controller.dto.ErrorResponseDTO;
import com.google.firebase.auth.FirebaseAuthException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // IllegalStateException,FirebaseAuthException,IllegalArgumentException
    @ExceptionHandler(FirebaseAuthException.class)
    public ResponseEntity<ErrorResponseDTO> handleAuthException(FirebaseAuthException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponseDTO(
                        "Unauthorized",
                        e.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(
                        "Conflict",
                        e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO("Not Found", e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneral(Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO(
                        "Internal Server Error",
                        "予期せぬエラーが発生しました: " + e.getMessage()));
    }
}
