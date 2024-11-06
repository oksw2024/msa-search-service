package com.example.booksearch.model;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultVO {
    private ResponseVO response;

    public ResponseVO getResponse() {
        return response;
    }
    public void setResponse(ResponseVO response) {
        this.response = response;
    }
}
