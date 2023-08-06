package com.techelevator.model.GoogleBooksAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleBookItems {
    private String id;
    private VolumeInfo volumeInfo;

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
