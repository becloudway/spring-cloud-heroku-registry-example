package com.xti.spring.cloud.heroku.discovery.example;

public class CreateNoteResponse {
    private Integer count;

    public CreateNoteResponse() {
    }

    public CreateNoteResponse(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
