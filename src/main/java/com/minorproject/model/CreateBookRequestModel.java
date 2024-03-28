package com.minorproject.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.minorproject.entity.Book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookRequestModel implements Serializable{
	
	private String name;

	private String authorName;
	
	private Integer numberOfPages;
	
	private String language;
	
	private Boolean available;
	
	private String genre;
	
	private String ISBN;
	
	private LocalDateTime publishedDate;

    public Book toBook(){
        return Book.builder()
                .name(name)
                .numberOfPages(numberOfPages)
                .language(language)
                .available(available)
                .genre(genre)
                .ISBN(ISBN)
                .publishedDate(publishedDate)
                .build();
    }
}
