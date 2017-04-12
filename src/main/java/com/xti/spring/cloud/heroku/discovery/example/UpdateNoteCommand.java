package com.xti.spring.cloud.heroku.discovery.example;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class UpdateNoteCommand {
    @TargetAggregateIdentifier
    private String noteId;
    private String text;

    public UpdateNoteCommand() {
    }

    public UpdateNoteCommand(String noteId, String text) {
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
