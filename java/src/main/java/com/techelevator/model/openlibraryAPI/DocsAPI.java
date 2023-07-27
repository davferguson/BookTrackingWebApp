package com.techelevator.model.openlibraryAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocsAPI {
    @JsonProperty("isbn")
    private String[] isbn;
    @JsonProperty("title")
    private String title;
    @JsonProperty("author_name")
    private String[] author;
    public String[] getIsbn() {
        return isbn;
    }

    public void setIsbn(String[] isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getAuthor() {
        return author;
    }

    public void setAuthor(String[] author) {
        this.author = author;
    }
}
