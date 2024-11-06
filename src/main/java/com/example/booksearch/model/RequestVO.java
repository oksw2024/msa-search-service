package com.example.booksearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestVO {
    // 검색 옵션에 따라 사용될 필드들
    private String keyword;
    private String title;
    private String author;

    // 검색 조건을 담는 필드
    private String searchType;

    public RequestVO() {
    }

    public RequestVO(String searchType, String value) {
        this.searchType = searchType;
        if ("title".equals(searchType)) {
            this.title = value;
        } else if ("author".equals(searchType)) {
            this.author = value;
        } else {
            this.keyword = value;  // 기본 검색어
        }
    }

    @JsonProperty("pageNo")
    private int pageNo;

    @JsonProperty("pageSize")
    private int pageSize;
}
