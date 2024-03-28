package com.minorproject.model;

import java.io.Serializable;

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
public class ReadAuthorRequestModel implements Serializable{
	private Integer id;
	
	private String name;
	
	private Integer age;
	
	private String email;
	
	private String country;
	
}
