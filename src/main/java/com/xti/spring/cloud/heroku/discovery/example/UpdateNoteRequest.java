package com.xti.spring.cloud.heroku.discovery.example;

public class UpdateNoteRequest {
    private String text;

    public UpdateNoteRequest() {
    }

    public UpdateNoteRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
