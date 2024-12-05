package com.example.booksearch.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.booksearch.model.BookExistResponse;
import com.example.booksearch.model.SearchResultVO;
import com.example.booksearch.util.UriBuilderUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BookService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    public SearchResultVO searchBooks(String searchType, String keyword, int page, int size) {
        // 검색어 변환
        String transformedKeyword = transformKeyword(keyword);
        URI uri = UriBuilderUtil.buildSearchUri(searchType, transformedKeyword, page, size);

        // 테스트용
        System.out.println("BACKEND Request URI (searchBooks) : " + uri);
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
    	CircuitBreaker circuitBreaker = circuitBreakerFactory.create("searchBooksCircuitBreaker");
    	
    	return circuitBreaker.run(() -> {
	        try {
	            String response = restTemplate.getForObject(uri, String.class);
	            return objectMapper.readValue(response, SearchResultVO.class);
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	            return null;
	        }
    	}, throwable -> fallbackSearchMethod(throwable));
    }
    
    private SearchResultVO fallbackSearchMethod(Throwable throwable) {
    	SearchResultVO fallbackResult = new SearchResultVO();
    	fallbackResult.setResponse(null);
    	return fallbackResult;
    }

    //대출여부확인
    public BookExistResponse checkBookExist(String isbn13, String libCode) {

        URI uri = UriBuilderUtil.buildBookExistUrl(isbn13, libCode);
        
     // 테스트용
        System.out.println("BACKEND Request URI (checkBookExist) : " + uri);
        
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("checkBookExistCircuitBreaker");

        return circuitBreaker.run(() -> {
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
        }, throwable -> fallbackExistMethod(throwable));
    }
    
    private BookExistResponse fallbackExistMethod(Throwable throwable) {
    	BookExistResponse fallbackResult = new BookExistResponse();
    	fallbackResult.setResponse(null);
    	return fallbackResult;
    }
}
