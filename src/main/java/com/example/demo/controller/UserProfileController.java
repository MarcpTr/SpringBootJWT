package com.example.demo.controller;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.exception.InvalidLogin;
import com.example.demo.model.User;
import com.example.demo.model.dto.ProfileDTO;
import com.example.demo.service.NoteService;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@CrossOrigin
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
    @DeleteMapping("/profile/remove")
    public ResponseEntity<?> removeUserProfile(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidLogin("Credenciales incorrectas");
        }
        String username = jwtTokenProvider.getUsernameFromToken(token);
        User user= userService.getUser(username);
        noteService.deleteByUserId(user.getId());
        userService.removeUser(user.getId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new userDeleted("Usuario Borrado"));

    }

}

@Getter
@Setter
@AllArgsConstructor
class userDeleted {
    private String mensaje;

}
