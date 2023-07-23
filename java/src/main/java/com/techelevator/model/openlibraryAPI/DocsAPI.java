package com.techelevator.model.openlibraryAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocsAPI {
    @JsonProperty("isbn")
    private String[] isbn;

    public String[] getIsbn() {
        return isbn;
    }

    public void setIsbn(String[] isbn) {
        this.isbn = isbn;
    }
}
