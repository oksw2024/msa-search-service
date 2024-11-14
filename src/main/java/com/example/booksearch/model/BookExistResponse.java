package com.example.booksearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookExistResponse {

    @JsonProperty("response")
    private Response response;

    public static class Response {
        @JsonProperty("request")
        private Request request;
        @JsonProperty("result")
        private Result result;

        @Getter
        @Setter
        public static class Request {
            private String isbn13;
            private String libCode;
        }

        @Getter
        @Setter
        public static class Result {
            private String hasBook;
            private String loanAvailable;
        }
    }
}
