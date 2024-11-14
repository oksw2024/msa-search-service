package com.example.booksearch.util;

import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

public class UriBuilderUtil {

    private static final String API_URL = "https://data4library.kr/api";
    private static final String AUTH_KEY = "246bc9a1a2ea4ba78b5ada1b16a0ba7e43537ef40b0427f80013629f7b593a86";

    public static URI buildSearchUri(String searchType, String keyword, int page, int size) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(API_URL + "/srchBooks")
                .queryParam("authKey", AUTH_KEY)
                .queryParam("pageSize", size)
                .queryParam("pageNo", page + 1) // 페이지 번호 1부터 시작
                .queryParam("format", "json");

        if ("title".equals(searchType)) {
            uriBuilder.queryParam("title", keyword);
        } else if ("author".equals(searchType)) {
            uriBuilder.queryParam("author", keyword);
        } else {
            uriBuilder.queryParam("keyword", keyword);
        }

        return uriBuilder.encode().build().toUri();
    }

    public static URI buildBookExistUrl(String isbn13, String libCode) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(API_URL + "/bookExist")
                .queryParam("authKey", AUTH_KEY)
                .queryParam("libCode", libCode)
                .queryParam("isbn13", isbn13)
                .queryParam("format", "json");

        return uriBuilder.encode().build().toUri();
    }
}
