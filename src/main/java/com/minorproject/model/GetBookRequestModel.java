package com.minorproject.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.minorproject.entity.Author;

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
public class GetBookRequestModel implements Serializable{

    private Integer id;
	
	private String name;

	private Author author;
	
	private Integer numberOfPages;
	
	private String language;
	
	private Boolean available;
	
	private String genre;
    
	private String ISBN;
	
	private LocalDateTime publishedDate;
    
}