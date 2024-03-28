package com.minorproject.model;

import java.io.Serializable;

import com.minorproject.entity.Student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStudentRequestModel implements Serializable{
	
	private Integer age;
	
	private String name;
	
	private String country;
	
	private String email;
	
	private String phoneNumber;
	
	public Student toStudent(){
		return Student.builder()
				.age(this.age)
				.name(this.name)
				.country(this.country)
				.email(this.email)
				.phoneNumber(phoneNumber)
				.build();
	}
}
