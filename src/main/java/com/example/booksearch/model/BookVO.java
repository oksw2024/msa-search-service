package com.example.booksearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookVO {
    @JsonProperty("bookname")
    private String bookname;

    @JsonProperty("authors")
    private String authors;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("publication_year")
    private String publicationYear;

    @JsonProperty("isbn13")
    private String isbn13;

    @JsonProperty("vol")
    private String vol;

    @JsonProperty("bookImageURL")
    private String bookImageURL; // 책표지 URL

    @JsonProperty("bookDtlUrl")
    private String bookDtlUrl; // 도서나루 URL

    @JsonProperty("loan_count")
    private String loanCount; // 대출 건수
}