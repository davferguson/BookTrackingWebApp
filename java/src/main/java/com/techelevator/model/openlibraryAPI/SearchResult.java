package com.techelevator.model.openlibraryAPI;

import com.techelevator.model.Book;

import java.util.List;

public class SearchResult {
    private int numFound;
    private List<Book> books;

    public int getNumFound() {
        return numFound;
    }

    public void setNumFound(int numFound) {
        this.numFound = numFound;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
