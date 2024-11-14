package com.example.booksearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    private String bookImageURL;

    @JsonProperty("bookDtlUrl")
    private String bookDtlUrl;

    @JsonProperty("loan_count")
    private String loanCount;
}
