package com.example.demo.controller;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.exception.InvalidLogin;
import com.example.demo.model.Note;
import com.example.demo.model.User;
import com.example.demo.model.dto.NoteDTO;
import com.example.demo.service.NoteService;
import com.example.demo.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class NoteController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;
    @GetMapping("/getNotes")
    public ResponseEntity<?> getNotes(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidLogin("Credenciales incorrectas");
        }
        String username = jwtTokenProvider.getUsernameFromToken(token);
        User user= userService.getUser(username);
        List<NoteDTO> notes= noteService.getNotesFromUser(user.getId());
        return ResponseEntity.ok(notes);
    }

    @PostMapping("/addNote")
    public ResponseEntity<?> addNote(@RequestHeader("Authorization") String authHeader, @RequestBody NoteBody noteBody) {
        String token = authHeader.substring(7);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidLogin("Credenciales incorrectas");
        }
        String username = jwtTokenProvider.getUsernameFromToken(token);
        User user= userService.getUser(username);
        NoteDTO response = noteService.addNote(noteBody.getTitle(), noteBody.getContent(), user);
        URI location;
        try {
             location = new URI("/api/getNote/" + response.getId());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.created(location).body(response);
    }
    @DeleteMapping("/removeNote/{noteId}")
    public ResponseEntity<?> removeNote(@RequestHeader("Authorization") String authHeader, @PathVariable Long noteId ){
        String token = authHeader.substring(7);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidLogin("Credenciales incorrectas");
        }
        String username = jwtTokenProvider.getUsernameFromToken(token);
        User user = userService.getUser(username);
        Note note = noteService.findByIdAndUserId(noteId, user.getId());

        noteService.delete(note);
        return ResponseEntity.ok("Nota eliminada correctamente");
    }
    @GetMapping("/getNote/{noteId}")
    public ResponseEntity<?> getNote(@RequestHeader("Authorization") String authHeader, @PathVariable Long noteId ){
        String token = authHeader.substring(7);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidLogin("Credenciales incorrectas");
        }
        String username = jwtTokenProvider.getUsernameFromToken(token);
        User user = userService.getUser(username);
        NoteDTO note = noteService.getNote(noteId, user);
        return ResponseEntity.ok(note);
    }
    @PatchMapping("/update/{noteId}")
    public ResponseEntity<?> updateNote(@RequestHeader("Authorization") String authHeader,
                                          @PathVariable Long noteId,
                                          @RequestBody NoteBody noteBody){
        String token = authHeader.substring(7);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidLogin("Credenciales incorrectas");
        }
        String username = jwtTokenProvider.getUsernameFromToken(token);
        User user = userService.getUser(username);
        NoteDTO noteDto=noteService.updateNote(noteId, noteBody.getTitle(), noteBody.getContent(), user);
        return ResponseEntity.ok(noteDto);
    }

}
@Getter
@Setter
class NoteBody{
    private String title;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;


}
