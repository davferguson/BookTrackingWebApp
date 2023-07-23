package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techelevator.model.openlibraryAPI.DocsAPI;

import java.util.List;

public class BookSearchAPI {
    @JsonProperty("numFound")
    private int numFound;

    @JsonProperty("docs")
    private List<DocsAPI> docs;

    public int getNumFound() {
        return numFound;
    }

    public void setNumFound(int numFound) {
        this.numFound = numFound;
    }

    public List<DocsAPI> getDocs() {
        return docs;
    }

    public void setDocs(List<DocsAPI> docs) {
        this.docs = docs;
    }
}
