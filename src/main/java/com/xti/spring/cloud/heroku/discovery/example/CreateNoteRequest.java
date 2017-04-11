package com.xti.spring.cloud.heroku.discovery.example;

public class CreateNoteRequest {
    private Integer count;
    private String text;

    public CreateNoteRequest() {
    }

    public CreateNoteRequest(Integer count, String text) {
        this.count = count;
        this.text = text;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
