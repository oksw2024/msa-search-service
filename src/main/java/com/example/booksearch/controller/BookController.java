package com.example.booksearch.controller;

import com.example.booksearch.model.BookExistResponse;
import com.example.booksearch.service.BookService;
import com.example.booksearch.model.SearchResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/list")
    public SearchResultVO searchBooks(
            Model model,
            @RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageNo", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "5") int size) {

        return bookService.searchBooks(searchType, keyword, page, size);
    }

    @GetMapping("/exist")
    public BookExistResponse checkBookExist(
            @RequestParam String isbn13,
            @RequestParam String libCode) {
        return bookService.checkBookExist(isbn13, libCode);
    }
}
