package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class NoteDTO {
    private Long id;
    private String title;
    private String content;
    private Timestamp createdAt;
    private Timestamp updateAt;
}