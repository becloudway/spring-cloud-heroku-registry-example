package com.xti.spring.cloud.heroku.discovery.example;

public class NoteCreatedEvent {
    private String noteId;
    private String text;

    public NoteCreatedEvent() {
    }

    public NoteCreatedEvent(String noteId, String text) {
        this.noteId = noteId;
        this.text = text;
    }

    public String getNoteId() {
        return noteId;
    }

    public String getText() {
        return text;
    }
}
