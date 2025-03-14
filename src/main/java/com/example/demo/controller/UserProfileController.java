package com.example.demo.controller;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.exception.InvalidLogin;
import com.example.demo.model.Note;
import com.example.demo.model.User;
import com.example.demo.model.dto.ProfileDTO;
import com.example.demo.service.NoteService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserProfileController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private NoteService noteService;
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidLogin("Credenciales incorrectas");
        }
        String username = jwtTokenProvider.getUsernameFromToken(token);
        User user= userService.getUser(username);
        ProfileDTO profileDTO= new ProfileDTO(user.getUsername(), user.getEmail(), user.getCreated_at());
        return ResponseEntity.ok(profileDTO);
    }

}
