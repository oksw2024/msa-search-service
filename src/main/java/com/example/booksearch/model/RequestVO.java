package com.example.booksearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestVO {
    private String keyword;
    private String title;
    private String author;

    private String searchType;

    @JsonProperty("pageNo")
    private int pageNo;

    @JsonProperty("pageSize")
    private int pageSize;

    public void setSearchCondition(String searchType, String value) {
        this.searchType = searchType;
        if ("title".equals(searchType)) {
            this.title = value;
        } else if ("author".equals(searchType)) {
            this.author = value;
        } else {
            this.keyword = value;
        }
    }
}
