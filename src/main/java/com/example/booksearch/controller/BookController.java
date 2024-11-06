package com.example.booksearch.controller;

import com.example.booksearch.service.BookService;
import com.example.booksearch.model.SearchResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/list")
    public String searchBooks(
            Model model,
            @RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageNo", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "5") int size) {

        // 서비스 호출하여 검색 결과와 모델 업데이트
        SearchResultVO resultVO = bookService.searchBooks(searchType, keyword, page, size);
        bookService.updateModelWithResults(model, resultVO, page, size, keyword, searchType);

        return "book/list";
    }
}
