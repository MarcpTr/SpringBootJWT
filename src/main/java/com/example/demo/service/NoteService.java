package com.example.demo.service;

import com.example.demo.exception.ResourceNotFound;
import com.example.demo.model.Note;
import com.example.demo.model.User;
import com.example.demo.model.dto.NoteDTO;
import com.example.demo.repository.NoteRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
public class NoteService {
    @Autowired
    NoteRepository noteRepository;

    public List<NoteDTO> getNotesFromUser(Long id)
    {   Optional<List<Note>> notes= noteRepository.findByUserId(id);
        if(notes.isEmpty()){
            return  List.of();
        }
        List<NoteDTO> noteDTO=notes.get().stream()
                .map(note -> new NoteDTO(note.getId(), note.getTitle(),note.getContent(), note.getCreatedAt(), note.getUpdatedAt()))
                .collect(Collectors.toList());
        return  noteDTO;
    }

    public NoteDTO getNote(Long id, User user) {

        Note note = noteRepository.findByIdAndUserId(id, user.getId()).orElseThrow(()-> new ResourceNotFound("Recurso no encontrado"));
        NoteDTO noteDTO=new NoteDTO(note.getId(), note.getTitle(),note.getContent(), note.getCreatedAt(), note.getUpdatedAt());
        return noteDTO;
    }

    public NoteDTO addNote(String title, String content, User user) {
        Note note = new Note();
        note.setUser(user);
        note.setTitle(title);
        note.setContent(content);
        note = noteRepository.save(note);
        NoteDTO noteDTO=new NoteDTO(note.getId(), note.getTitle(),note.getContent(), note.getCreatedAt(), note.getUpdatedAt());
        return noteDTO;
    }

    public NoteDTO updateNote(Long noteId, String title, String content, User user) {
        Note note = new Note();
        note.setId(noteId);
        note.setUser(user);
        note.setTitle(title);
        note.setContent(content);
        note = noteRepository.saveAndFlush(note);
        NoteDTO noteDTO=new NoteDTO(note.getId(), note.getTitle(),note.getContent(), note.getCreatedAt(), note.getUpdatedAt());
        return noteDTO;
    }
    public Note findByIdAndUserId(Long noteId, Long id) {
        return noteRepository.findByIdAndUserId(noteId, id).orElseThrow(()-> new ResourceNotFound("Recurso no encontrado"));
    }

    public void delete(Note note) {
        noteRepository.delete(note);
    }
}
