package com.ezen.booktving.dto;

import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.booktving.entity.AuthorBook;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorBookDto {


	private Long id;
	
	@NotBlank(message = "필수입력 값입니다.")
	private String bookName;
	
	@NotBlank(message = "필수입력 값입니다.")
	private String bookSubTitle;
	
	@NotBlank(message = "필수입력 값입니다.")
	private String bookIntrodution;

	 private MultipartFile authorBookImgFile;
	
	@NotBlank(message = "필수입력 값입니다.")
	private String oriImgName;
	
	@NotBlank(message = "필수입력 값입니다.")
	private String imgUrl;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public AuthorBook creAuthorBook() {
		return modelMapper.map(this, AuthorBook.class);
	}
	
	public static AuthorBookDto of(AuthorBook authorBook) {
		return modelMapper.map(authorBook, AuthorBookDto.class);
	}
	
}
