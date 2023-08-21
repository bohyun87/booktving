package com.ezen.booktving.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.booktving.dto.BookSearchDto;
import com.ezen.booktving.dto.SearchBookDto;
import com.ezen.booktving.service.ApiService;
import com.ezen.booktving.service.SearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SearchController {
	
	private final ApiService apiService;
	private final SearchService searchService;
	
	@GetMapping(value = "/search/main")
	public String searchMain(Model model) {
		model.addAttribute("bookSearchDto", new BookSearchDto());
		return "search/searchMain";
	}
	
	@GetMapping(value = "/search/detail")
	public String searchDetail(BookSearchDto bookSearchDto, Model model) {
		
		try {
			List<String> searchBookIsbnList = apiService.getSearchBookIsbnList(bookSearchDto);
			for(String isbn : searchBookIsbnList) {
				apiService.getBookInfoByAladinApi(isbn);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Pageable pageble = PageRequest.of(0, 30);
		Slice<SearchBookDto> searchBookList = searchService.getSearchBookList(null, bookSearchDto, pageble);
		Long searchCount = searchService.getSearchBookCount(bookSearchDto);
		
		model.addAttribute("bookSearchDto", bookSearchDto);
		model.addAttribute("searchBookList", searchBookList);
		model.addAttribute("searchCount", searchCount);
		model.addAttribute("pageble", pageble);
		
		return "search/searchDetail";
	}
	
	// 무한스크롤시 필요한 데이터 넘겨주기
	@PostMapping(value = "/search/detail/paging")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getNextSearchBookList(@RequestBody Map<String, Object> data) {
		
		Long lastBookId = ((Integer) data.get("lastBookId")).longValue();
		
		Map<String, Object> bookSearchDtoMap = (HashMap) data.get("bookSearchDto");
		BookSearchDto bookSearchDto = new BookSearchDto((String) bookSearchDtoMap.get("searchBy"), (String) bookSearchDtoMap.get("searchQuery"));
		
		Map<String ,Object> pageableMap = (HashMap) data.get("pageable");
		Pageable pageable = PageRequest.of((Integer)pageableMap.get("pageNumber") + 1, 30);
		
		Slice<SearchBookDto> searchBookList = searchService.getSearchBookList(lastBookId , bookSearchDto, pageable);
		
		Map<String, Object> result = new HashMap<>();
		result.put("bookSearchDto", bookSearchDto);
		result.put("searchBookList", searchBookList);
		
		return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
	}
}
