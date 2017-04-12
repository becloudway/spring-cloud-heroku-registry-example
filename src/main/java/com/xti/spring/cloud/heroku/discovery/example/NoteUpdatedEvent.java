package com.xti.spring.cloud.heroku.discovery.example;

public class NoteUpdatedEvent {
    private String noteId;
    private String text;

    public NoteUpdatedEvent() {
    }

    public NoteUpdatedEvent(String noteId, String text) {
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
