package com.minorproject.model;

import java.io.Serializable;

import com.minorproject.entity.Author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAuthorRequestModel implements Serializable{
	
	private String name;
	
	private Integer age;
	
	private String email;
	
	private String country;

	public Author toAuthor(){
		return Author.builder()
				.name(this.name)
				.age(this.age)
				.email(this.email)
				.country(this.country)
				.build();
	}
}
