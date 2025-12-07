package com.example.room_reservation_system_api.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.room_reservation_system_api.driver.FirebaseAuthDriver;
import com.example.room_reservation_system_api.entity.User;
import com.example.room_reservation_system_api.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        userRepository = this.userRepository;
    }

    // FirebaseのidTokenを受け取って、そのidTokenに対応するユーザがデータベースにあれば、それを返し、なければ新しく作って返す
    public User loadOrCreateUser(String idToken) throws FirebaseAuthException {
        FirebaseToken token = FirebaseAuthDriver.verifyToken(idToken);
        String uid = token.getUid();
        Optional<User> user = userRepository.findById(uid);
        if (user.isPresent()) {
            return user.get();
        } else {
            User newUser = User.create(uid, token.getName(), token.getEmail());
            userRepository.save(newUser);
            return newUser;
        }
    }
}
