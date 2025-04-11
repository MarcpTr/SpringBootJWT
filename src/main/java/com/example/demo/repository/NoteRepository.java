package com.example.demo.repository;

import com.example.demo.model.Note;
import com.example.demo.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NoteRepository  extends JpaRepository<Note, Long> {

    Optional<List<Note>> findByUserId(Long id);
    Optional<Note> findByIdAndUserId(Long noteId, Long userId);

    void deleteByUserId(Long id);
}
