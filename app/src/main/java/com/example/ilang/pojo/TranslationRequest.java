package com.example.ilang.pojo;

public class TranslationRequest {
    private String from;
    private String to;
    private String query;

    public TranslationRequest(String from, String to, String query) {
        this.from = from;
        this.to = to;
        this.query = query;
    }
}

