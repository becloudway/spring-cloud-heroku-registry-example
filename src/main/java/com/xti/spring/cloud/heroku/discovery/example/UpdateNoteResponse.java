package com.xti.spring.cloud.heroku.discovery.example;

public class UpdateNoteResponse {
    private String noteId;

    public UpdateNoteResponse() {
    }

    public UpdateNoteResponse(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }
}
