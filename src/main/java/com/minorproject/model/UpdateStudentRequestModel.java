package com.minorproject.model;



import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UpdateStudentRequestModel implements Serializable{
	private Integer age;
	
	private String name;
	
	private String country;
	
	private String email;
	
	private String phoneNumber;
}
