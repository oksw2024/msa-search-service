package com.example.booksearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseVO {
    @JsonProperty("request")
    private RequestVO request;

    @JsonProperty("numFound")
    private int numFound;

    @JsonProperty("docs")
    private List<DocWrapperVO> docs;
}