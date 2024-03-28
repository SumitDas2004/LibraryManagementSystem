package com.minorproject.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.minorproject.entity.Card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadStudentRequestModel implements Serializable{
	private Integer age;
	
	private String name;
	
	private String country;
	
	private String email;
	
	private String phoneNumber;
	
	private Integer id;
	
	private Card card;
	
	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;
	
}
