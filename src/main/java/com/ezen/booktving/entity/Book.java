package com.ezen.booktving.entity;

import java.time.LocalDate;

import com.ezen.booktving.dto.BookImgDto;
import com.ezen.booktving.dto.BookRegFormDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "book")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book extends BaseTimeEntity {
	
	@Id
	@Column(name = "book_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String bookName;
	
	@Column(nullable = false, unique = true)
	private String isbn;
	
	@Column(nullable = false)
	private String author;
	
	@Column(nullable = false)
	private String publisher;
	
	@Column(nullable = false)
	private LocalDate publicationDate;
	
	@Column(columnDefinition = "text", nullable = false)
	private String bookIntroduction;
	
	@Column(nullable = false)
	private String category;
	
	@Column(nullable = false)
	private Integer page;
	
	@Column(columnDefinition = "text", nullable = false)
	private String contents;
	
	@Column(columnDefinition = "text", nullable = false)
	private String authorInfo;

	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
	private List<BookImg> bookImgList;
	
	public void updateBookDetail(Integer page, String contents, String reqAuthor, String authorInfo) {
		this.page = page;
		this.contents = contents;
		this.reqAuthor = reqAuthor;
		this.authorInfo = authorInfo;
	}

	//book 엔티티 수정
	public void updateBook(BookRegFormDto bookRegFormDto) {
		this.bookName = bookRegFormDto.getBookName();
		this.isbn = bookRegFormDto.getIsbn();
		this.author = bookRegFormDto.getAuthor();
		this.publisher = bookRegFormDto.getPublisher();
		this.page = bookRegFormDto.getPage();
		this.publicationDate = bookRegFormDto.getPublicationDate();
		this.bookIntroduction = bookRegFormDto.getBookIntroduction();
		this.contents = bookRegFormDto.getContents();
		this.category = bookRegFormDto.getCategory();
	}

	public List<BookImgDto> getBookImgDtoList() {
		return bookImgList.stream()
	            .map(BookImgDto::of) // BookImg 엔티티를 BookImgDto로 변환
	            .collect(Collectors.toList());
	}
}
