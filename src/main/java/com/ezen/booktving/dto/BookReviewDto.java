package com.ezen.booktving.dto;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import com.ezen.booktving.entity.Book;
import com.ezen.booktving.entity.BookReview;
import com.ezen.booktving.entity.Member;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class BookReviewDto {

	@NotBlank(message = "아이디는 필수입력 값입니다.")
	private Long id;
	
	@NotBlank(message = "내용은 필수 입니다.")
	@Length(min = 2, max = 50, message = "리뷰는 반드시 2자 이상 입력해주세요.")
	private String content;
	
	private Book book;
	
	private Member member;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public BookReview createBookReview() {
		return modelMapper.map(this, BookReview.class);
	}
	
	public static BookReviewDto of(BookReview bookReview) {
		return modelMapper.map(bookReview, BookReviewDto.class);
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	public void setMember(Member member) {
		this.member = member;
	}
}