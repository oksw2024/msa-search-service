package com.example.booksearch.service;

import com.example.booksearch.model.BookExistResponse;
import com.example.booksearch.model.BookVO;
import com.example.booksearch.model.DocWrapperVO;
import com.example.booksearch.model.SearchResultVO;
import com.example.booksearch.util.UriBuilderUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public SearchResultVO searchBooks(String searchType, String keyword, int page, int size) {
        // 검색어 변환
        String transformedKeyword = transformKeyword(keyword);
        URI uri = UriBuilderUtil.buildSearchUri(searchType, transformedKeyword, page, size);

        // 테스트용
        System.out.println("BACKEND Request URI: " + uri);
        // API 호출 및 결과 반환
        return fetchSearchResults(uri);
    }

    private String transformKeyword(String keyword) {
        if (keyword != null) {
            return String.join(";", keyword.trim().split("\\s+"));
        }
        return null;
    }

    private SearchResultVO fetchSearchResults(URI uri) {
        try {
            String response = restTemplate.getForObject(uri, String.class);
            return objectMapper.readValue(response, SearchResultVO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateModelWithResults(Model model, SearchResultVO resultVO, int page, int size, String keyword, String searchType) {
        if (resultVO == null || resultVO.getResponse() == null || resultVO.getResponse().getDocs() == null) {
            model.addAttribute("books", Collections.emptyList());
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", 0);
            model.addAttribute("hasNext", false);
            model.addAttribute("hasPrevious", false);
            model.addAttribute("keyword", keyword);
            model.addAttribute("searchType", searchType);
            System.out.println("No books found in response.");
        } else {
            List<DocWrapperVO> books = resultVO.getResponse().getDocs();
            int totalElements = resultVO.getResponse().getNumFound();
            Pageable pageable = PageRequest.of(page, size);
            Page<DocWrapperVO> pagedBooks = new PageImpl<>(books, pageable, totalElements);

            model.addAttribute("books", pagedBooks.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", pagedBooks.getTotalPages());
            model.addAttribute("hasNext", pagedBooks.hasNext());
            model.addAttribute("hasPrevious", pagedBooks.hasPrevious());
            model.addAttribute("keyword", keyword);
            model.addAttribute("searchType", searchType);
        }
    }

    public BookExistResponse checkBookExist(String isbn13, String libCode) {

        URI uri = UriBuilderUtil.buildBookExistUrl(isbn13, libCode);

        // 테스트용
        System.out.println("BACKEND Request URI: " + uri);

        try {
            // OpenAPI 호출
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(uri, String.class);

            // JSON 응답 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response, BookExistResponse.class);

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch book existence data", e);
        }
    }
}
