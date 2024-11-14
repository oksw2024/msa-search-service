package com.example.booksearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocWrapperVO {
    @JsonProperty("doc")
    private BookVO doc;

    // Getters and Setters
    public BookVO getDoc() {
        return doc;
    }

    public void setDoc(BookVO doc) {
        this.doc = doc;
    }
}
