package com.techelevator.model.GoogleBooksAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleBookSearchResult {
    @JsonProperty("totalItems")
    private int totalItems;
    @JsonProperty("items")
    private GoogleBookItems[] items;

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public GoogleBookItems[] getItems() {
        return items;
    }

    public void setItems(GoogleBookItems[] items) {
        this.items = items;
    }
}
